package com.devpm.vocabuilder.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.devpm.vocabuilder.App
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.activities.ImageActivity
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SettingsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SettingsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val binding: FragmentSettingsBinding by lazy {
        FragmentSettingsBinding.inflate(layoutInflater)
    }

    // Pass app from MainActivity
    private val app: App by lazy { requireActivity().application as App }

    // Define userDao
    private val userDao by lazy {
        app.db.userDao()
    }

    private var avatarUri: Uri? = null

    // Copy InputStream to the app's inner local file storage
    private fun copyFileToInternalStorage(inputStream: InputStream, fileName: String): File? {
        return try {
            val file = File(requireContext().filesDir, fileName)
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
            file
        } catch (e: Exception) {
            Log.e("SettingsFragment", "Error while copying the file: $e")
            null
        } finally {
            inputStream.close()
        }
    }

    // Generate file name, may use the original name or make a new one
    private fun generateFileName(uri: Uri): String {
        val cursor = requireContext().contentResolver.query(uri, null, null, null, null)
        val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1
        var fileName = "avatar_${System.currentTimeMillis()}.jpg"
        if (cursor != null && cursor.moveToFirst() && nameIndex >= 0) {
            fileName = cursor.getString(nameIndex)
        }
        cursor?.close()
        return fileName
    }

    // Use OpenDocument contract for long-term file access
    private val getImageFromGallery = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            // Request long-term permissions
            val contentResolver = requireActivity().contentResolver

            // Check persistable permissions
            try {
                contentResolver.takePersistableUriPermission(
                    it,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                )
            } catch (e: SecurityException) {
                Log.w("SettingsFragment", "Не удалось взять персистентные права: $e")
            }

            avatarUri = it
            binding.avatarImg.setImageURI(it)
            saveUserAvatar(it)
        }
    }
    private val getImgFromGallery = registerForActivityResult(ActivityResultContracts.OpenDocument()) { uri: Uri? ->
        uri?.let {
            // Получаем InputStream выбранного файла
            val inputStream = requireContext().contentResolver.openInputStream(it)
            if (inputStream != null) {
                // Копируем файл во внутреннее хранилище
                val destFile = copyFileToInternalStorage(inputStream, generateFileName(it))
                if (destFile != null) {
                    avatarUri = Uri.fromFile(destFile)
                    binding.avatarImg.setImageURI(avatarUri)
                    saveUserAvatar(avatarUri!!)
                }
            }
        }
    }

    private fun populateSettings(user: User) {
        user.avatarUri?.let {
            val uri = Uri.parse(it)
            binding.avatarImg.setImageURI(uri)
        }
    }

    private fun saveUserAvatar(uri: Uri) {
        // @TODO: Implement image saving (Uri or file copy)
        // Save Uri in user model for simplicity
        val user = app.user!!
        user.avatarUri = uri.toString()
        // Save async
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    userDao.updateUser(user)
                }
                // Handle saving success
                Snackbar.make(binding.root, "Данные пользователя успешно сохранены", Snackbar.LENGTH_LONG).apply {
                    setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.successT))
                    setAnchorView(binding.avatarImg)
                    show()
                }
            } catch (exc: Exception) {
                // Handle saving error
                Snackbar.make(binding.root, "Ошибка при сохранении данных: ${exc.message}", Snackbar.LENGTH_LONG).apply {
                    setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.errorT))
                    setAnchorView(binding.avatarImg)
                    show()
                }
                exc.message?.let { Log.e("Settings", it) }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app.user?.let { populateSettings(it) }

        binding.avatarImg.setOnClickListener {
            app.user?.avatarUri?.let { uri ->
                val intent = Intent(context, ImageActivity::class.java)
                intent.putExtra("imageUri", uri.toString())
                startActivity(intent)
            } ?: run {
                Toast.makeText(context, "Изображение не выбрано", Toast.LENGTH_SHORT).show()
            }
        }

        binding.changeBtn.setOnClickListener {
            // Open selector for persisted images
            getImageFromGallery.launch(arrayOf("image/*"))
            //getImgFromGallery.launch(arrayOf("image/*"))
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SettingsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
