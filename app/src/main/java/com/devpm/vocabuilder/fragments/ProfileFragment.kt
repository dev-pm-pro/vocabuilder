package com.devpm.vocabuilder.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.devpm.vocabuilder.App
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.databinding.FragmentProfileBinding

import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private val binding: FragmentProfileBinding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }

    // Pass app from MainActivity
    private val app: App by lazy { requireActivity().application as App }

    // Define userDao
    private val userDao by lazy {
        app.db.userDao()
    }
    private fun populateProfile(user: User) {
        binding.loginView.setValue(user.login)
        binding.firstNameView.setValue(user.firstName)
        binding.lastNameView.setValue(user.lastName)
        binding.emailView.setValue(user.email)
        binding.birthdateView.setValue(user.birthDate)
        binding.phoneView.setValue(user.phone)
    }

    // Subscribe for changes and save on checkImg click
    private fun setEditHandlers()
    {
        binding.loginView.onValueSaved = { newValue ->
            updateUserField("login", newValue)
        }
        binding.firstNameView.onValueSaved = { newValue ->
            updateUserField("firstName", newValue)
        }
        binding.lastNameView.onValueSaved = { newValue ->
            updateUserField("lastName", newValue)
        }
        binding.emailView.onValueSaved = { newValue ->
            updateUserField("email", newValue)
        }
        binding.birthdateView.onValueSaved = { newValue ->
            updateUserField("birthDate", newValue)
        }
        binding.phoneView.onValueSaved = { newValue ->
            updateUserField("phone", newValue)
        }
    }

    private fun updateUserField(fieldName: String, newValue: String) {
        val user = app.user ?: return
        when (fieldName) {
            "login" -> user.login = newValue
            "firstName" -> user.firstName = newValue
            "lastName" -> user.lastName = newValue
            "email" -> user.email = newValue
            "birthDate" -> user.birthDate = newValue
            "phone" -> user.phone = newValue
        }
        // Save async
        lifecycleScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    userDao.updateUser(user)
                }
                // Handle saving success
                Snackbar.make(binding.root, "Данные пользователя успешно сохранены", Snackbar.LENGTH_LONG).apply {
                    setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.successT))
                    setAnchorView(binding.updatePwdBtn)
                    show()
                }
            } catch (exc: Exception) {
                // Handle saving error
                Snackbar.make(binding.root, "Ошибка при сохранении данных: ${exc.message}", Snackbar.LENGTH_LONG).apply {
                    setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.errorT))
                    setAnchorView(binding.updatePwdBtn)
                    show()
                }
                exc.message?.let { Log.e("Profile", it) }
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
        app.user?.let { populateProfile(it) }
        setEditHandlers()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
