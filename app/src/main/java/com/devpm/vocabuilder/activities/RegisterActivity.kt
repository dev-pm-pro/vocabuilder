package com.devpm.vocabuilder.activities

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devpm.vocabuilder.App
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.Utils
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.ZoneId

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val controls by lazy {
        listOf(
            binding.loginView, binding.passwordView, binding.passRepeatView,
            binding.firstNameView, binding.lastNameView,
            binding.birthdateView, binding.emailView, binding.phoneView
        )
    }

    private val app: App by lazy {
        application as App
    }
    private val userDao by lazy {
        app.db.userDao()
    }

    private fun clearAll() {
        controls.forEach { it.clearValue() }
    }
    private suspend fun saveUser(user: User): Boolean {
        return try {
            userDao.insertUser(user)
            true
        } catch (e: Exception) {
            Log.e("saveUser", "Error while saving user", e)
            false
        }
    }
    private suspend fun trySaveUser(user: User): Boolean {
        val success = saveUser(user)
        if (success) return true

        // Switch to Main thread to show Snackbar
        withContext(Dispatchers.Main) {
            Snackbar.make(binding.root, R.string.register_failure_text, Snackbar.LENGTH_LONG).also { msg ->
                msg.setAction(getString(R.string.retry_text).uppercase()) {
                    // Relaunch saving on button click inside the main coroutine
                    CoroutineScope(Dispatchers.Main).launch {
                        trySaveUser(user)
                    }
                }
                msg.setBackgroundTint(ContextCompat.getColor(this@RegisterActivity, R.color.errorT))
                msg.show()
            }
        }
        return false
    }
    private fun validateUser(user: User) : Boolean {
        return true
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerBtn.setOnClickListener {
            val user = User(
                login = binding.loginView.getValue()!!,
                password = binding.passwordView.getValue()!!,
                firstName = binding.firstNameView.getValue(),
                lastName = binding.lastNameView.getValue(),
                birthDate = binding.birthdateView.getValue(),
                email = binding.emailView.getValue(),
                phone = binding.phoneView.getValue(),
                created = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
            )
            if (!validateUser(user)) {
                Snackbar.make(binding.root, R.string.check_form_text, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(ContextCompat.getColor(this@RegisterActivity, R.color.warningT))
                    show()
                }
                return@setOnClickListener // explicit return from lambda
            }

            CoroutineScope(Dispatchers.IO).launch {
                val saved = trySaveUser(user)
                if (saved) {
                    withContext(Dispatchers.Main) {
                        val message = getString(R.string.register_success_text, user.login)
                        val spannable = Utils.highlightFragment(message, user.login)
                        Toast.makeText(this@RegisterActivity, spannable, Toast.LENGTH_SHORT).show()

                        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

        binding.loginLabel.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.resetBtn.setOnClickListener {
            clearAll()
        }
    }
}
