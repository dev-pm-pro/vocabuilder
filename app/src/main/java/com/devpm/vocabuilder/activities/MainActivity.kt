package com.devpm.vocabuilder.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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

    // Элементы навигации
    private val ctrls by lazy {
        listOf(
            Pair(binding.decksImg, binding.decksLbl),
            Pair(binding.profileImg, binding.profileLbl),
            Pair(binding.settingsImg, binding.settingsLbl),
            Pair(binding.statsImg, binding.statsLbl)
        )
    }

    // Функция замены фрагментов
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, fragment)
            .commit()
    }

    // Сброс цвета всех кнопок/иконок в неактивный цвет
    private fun resetActiveState() {
        val inactiveColor = ContextCompat.getColor(this, R.color.cyanDarker)
        ctrls.forEach {
            it.first.setColorFilter(inactiveColor)
            it.second.setTextColor(inactiveColor)
        }
    }

    // Установка активного цвета
    private fun setActiveState(ctl: Pair<ImageView, TextView>) {
        val activeColor = ContextCompat.getColor(this, R.color.magentaDark)
        ctl.first.setColorFilter(activeColor)
        ctl.second.setTextColor(activeColor)
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
            resetActiveState()
            setActiveState(ctrls[0])
        }
        binding.profileBtn.setOnClickListener {
            replaceFragment(profile)
            resetActiveState()
            setActiveState(ctrls[1])
        }
        binding.settingsBtn.setOnClickListener {
            replaceFragment(settings)
            resetActiveState()
            setActiveState(ctrls[2])
        }
        binding.statsBtn.setOnClickListener {
            replaceFragment(stats)
            resetActiveState()
            setActiveState(ctrls[3])
        }

        val intent = Intent(this@MainActivity, LoginActivity::class.java)
        //startActivity(intent)
        //finish()
    }
}
