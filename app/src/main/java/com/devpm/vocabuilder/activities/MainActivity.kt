package com.devpm.vocabuilder.activities

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.fragments.ProfileFragment
import com.devpm.vocabuilder.fragments.SettingsFragment

class MainActivity : AppCompatActivity() {
    // Объекты фрагментов
    private val profile = ProfileFragment()
    private val settings = SettingsFragment()

    // Функция замены фрагментов
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Устанавливаем начальный фрагмент
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, profile)
            .commit()

        // Обработчики нажатий
        findViewById<LinearLayout>(R.id.profileBtn).setOnClickListener {
            replaceFragment(profile)
        }
        findViewById<LinearLayout>(R.id.settingsBtn).setOnClickListener {
            replaceFragment(settings)
        }

        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        //startActivity(intent)
        //finish()
    }
}
