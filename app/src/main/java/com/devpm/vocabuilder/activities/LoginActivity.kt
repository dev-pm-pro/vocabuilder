package com.devpm.vocabuilder.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devpm.vocabuilder.App
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.databinding.ActivityLoginBinding
import com.devpm.vocabuilder.Utils
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

sealed class AuthResult {
    data object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val app: App by lazy { application as App }
    private val userDao by lazy { app.db.userDao() }

    private fun authenticateUser(login: String, password: String, onResult: (AuthResult) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val user = userDao.getUserByLogin(login)
                if (user == null) {
                    withContext(Dispatchers.Main) {
                        onResult(AuthResult.Error(getString(R.string.user_not_found_text)))
                    }
                } else if (user.password != password) {
                    withContext(Dispatchers.Main) {
                        onResult(AuthResult.Error((getString(R.string.wrong_password_text))))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        onResult(AuthResult.Success)
                    }
                }
            } catch (e: Exception) {
                Log.e("LoginActivity", "DB error", e)
                withContext(Dispatchers.Main) {
                    onResult(AuthResult.Error((getString(R.string.login_failure_text))))
                }
            }
        }
    }

    private fun validateUser(login: String, password: String) : Boolean {
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val login = binding.loginBox.text.toString()
            val password = binding.passwordBox.text.toString()
            if (!validateUser(login, password)) {
                Snackbar.make(binding.root, R.string.not_empty_form_text, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(ContextCompat.getColor(this@LoginActivity, R.color.warningT))
                    setAnchorView(binding.loginBtn)
                    show()
                }
                return@setOnClickListener // explicit return from lambda
            }

            authenticateUser(login, password) { result ->
                if (result is AuthResult.Error) {
                    Snackbar.make(binding.root, result.message, Snackbar.LENGTH_LONG).apply {
                        setBackgroundTint(ContextCompat.getColor(this@LoginActivity, R.color.errorT))
                        setAnchorView(binding.loginBtn)
                        show()
                    }
                    return@authenticateUser
                }

                val message = getString(R.string.login_success_text, login)
                val spannable = Utils.highlightFragment(message, login)
                Toast.makeText(this, spannable, Toast.LENGTH_SHORT).show()

                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.registerLabel.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
