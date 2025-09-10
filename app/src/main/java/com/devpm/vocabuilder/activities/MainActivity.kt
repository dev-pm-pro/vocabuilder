package com.devpm.vocabuilder.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.devpm.vocabuilder.App
import com.devpm.vocabuilder.R
import com.devpm.vocabuilder.databinding.ActivityMainBinding
import com.devpm.vocabuilder.fragments.DecksFragment
import com.devpm.vocabuilder.fragments.ProfileFragment
import com.devpm.vocabuilder.fragments.SettingsFragment
import com.devpm.vocabuilder.fragments.StatsFragment

class MainActivity : AppCompatActivity() {
    // Declare ViewBinding
    private lateinit var binding: ActivityMainBinding
    // Application object
    private val app: App by lazy { application as App }
    // Fragment objects
    private val decks = DecksFragment()
    private val profile = ProfileFragment()
    private val settings = SettingsFragment()
    private val stats = StatsFragment()

    // Navigation elements
    private val ctrls by lazy {
        mapOf(
            "decks" to Pair(binding.decksImg, binding.decksLbl),
            "profile" to Pair(binding.profileImg, binding.profileLbl),
            "settings" to Pair(binding.settingsImg, binding.settingsLbl),
            "stats" to Pair(binding.statsImg, binding.statsLbl)
        )
    }

    // Fragments replacement function
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, fragment)
            .commit()
    }

    // Reset color to inactive state for all icons and labels
    private fun resetActiveState() {
        val inactiveColor = ContextCompat.getColor(this, R.color.cyanDarker)
        ctrls.values.forEach { (img, lbl) ->
            img.setColorFilter(inactiveColor)
            lbl.setTextColor(inactiveColor)
        }
    }
    // Set active color
    private fun setActiveState(key: String) {
        val activeColor = ContextCompat.getColor(this, R.color.magentaDark)
        ctrls[key]?.let { (img, lbl) ->
            img.setColorFilter(activeColor)
            lbl.setTextColor(activeColor)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Create object from layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set initial fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, profile)
            .commit()

        // Click handlers
        binding.decksBtn.setOnClickListener {
            replaceFragment(decks)
            resetActiveState()
            setActiveState("decks")
        }
        binding.profileBtn.setOnClickListener {
            replaceFragment(profile)
            resetActiveState()
            setActiveState("profile")
        }
        binding.settingsBtn.setOnClickListener {
            replaceFragment(settings)
            resetActiveState()
            setActiveState("settings")
        }
        binding.statsBtn.setOnClickListener {
            replaceFragment(stats)
            resetActiveState()
            setActiveState("stats")
        }

        if (app.user == null) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
            return
        }
    }
}
