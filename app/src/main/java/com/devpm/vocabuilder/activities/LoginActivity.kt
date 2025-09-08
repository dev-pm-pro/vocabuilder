package com.devpm.vocabuilder.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.devpm.vocabuilder.App
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.databinding.ActivityLoginBinding
import com.devpm.vocabuilder.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val app: App by lazy { application as App }
    private val userDao by lazy { app.db.userDao() }

    private fun validateUser(login: String, password: String) : Boolean {
        return false
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
        }
    }
}
