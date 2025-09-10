package com.devpm.vocabuilder.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.databinding.ActivityMainBinding
import com.devpm.vocabuilder.fragments.DecksFragment
import com.devpm.vocabuilder.fragments.ProfileFragment
import com.devpm.vocabuilder.fragments.SettingsFragment
import com.devpm.vocabuilder.fragments.StatsFragment

class MainActivity : AppCompatActivity() {
    // объявление ViewBinding
    private lateinit var binding: ActivityMainBinding
    // Объекты фрагментов
    private val decks = DecksFragment()
    private val profile = ProfileFragment()
    private val settings = SettingsFragment()
    private val stats = StatsFragment()

    // Функция замены фрагментов
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, fragment)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Создание объекта из разметки
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Устанавливаем начальный фрагмент
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, profile)
            .commit()

        // Обработчики нажатий
        binding.decksBtn.setOnClickListener {
            replaceFragment(decks)
        }
        binding.profileBtn.setOnClickListener {
            replaceFragment(profile)
        }
        binding.settingsBtn.setOnClickListener {
            replaceFragment(settings)
        }
        binding.statsBtn.setOnClickListener {
            replaceFragment(stats)
        }

        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        //startActivity(intent)
        //finish()
    }
}
