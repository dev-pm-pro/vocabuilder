package com.devpm.vocabuilder.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.devpm.vocabuilder.App
import com.devpm.vocabuilder.databinding.FragmentSettingsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

    // Register callback to get the resulting image chosen from gallery
    private val getImageFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            avatarUri = it
            binding.avatarImg.setImageURI(it)
            saveUserAvatar(it)
        }
    }

    private fun saveUserAvatar(uri: Uri) {
        // @TODO: Implement image saving (Uri or file copy)
        // Save Uri in user model for simplicity
        val user = app.user!!
        user.avatarUri = uri.toString()
        CoroutineScope(Dispatchers.IO).launch {
            userDao.updateUser(user)
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

        binding.avatarImg.setOnClickListener {
            // Open gallery to choose image type
            getImageFromGallery.launch("image/*")
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
