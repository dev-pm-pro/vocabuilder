package com.devpm.vocabuilder.activities

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.data.models.User
import com.devpm.vocabuilder.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private fun saveUser(user: User): Boolean {
        return false
    }
    private fun trySaveUser(user: User): Boolean {
        if (saveUser(user)) return true
        Snackbar.make(binding.root, R.string.register_failure_text, Snackbar.LENGTH_LONG).also { msg ->
            msg.setAction(getString(R.string.retry_text).uppercase()) {
                trySaveUser(user)
            }
            msg.setBackgroundTint(ContextCompat.getColor(this@RegisterActivity, R.color.errorT))
            msg.show()
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
                login = "testLogin",
                password = "testPass"
            )
            if (!validateUser(user)) {
                Snackbar.make(binding.root, R.string.check_form_text, Snackbar.LENGTH_SHORT).apply {
                    setBackgroundTint(ContextCompat.getColor(this@RegisterActivity, R.color.warningT))
                    show()
                }
                return@setOnClickListener // explicit return from lambda
            }
            if (!trySaveUser(user))
                return@setOnClickListener

            val login = user.login
            val message = getString(R.string.register_success_text, user.login)

            val spannable = SpannableStringBuilder(message)
            val start = message.indexOf(login)
            val end = start + login.length

            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            Toast.makeText(this, spannable, Toast.LENGTH_SHORT).show()
        }
    }
}
