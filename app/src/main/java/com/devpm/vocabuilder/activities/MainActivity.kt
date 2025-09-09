package com.devpm.vocabuilder.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {
    private val profile = ProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Устанавливаем начальный фрагмент
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, profile)
            .commit()

        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        //startActivity(intent)
        //finish()
    }
}
