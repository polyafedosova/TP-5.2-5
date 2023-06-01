package ru.vsu.cs.tp.paws

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import medical.MedicalFragment
import ru.vsu.cs.tp.paws.databinding.ActivityMainBinding
import java.sql.Time
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var sharedPreferencesToken: SharedPreferences

    private lateinit var splashScreenFragment: SplashFragment

    private companion object {
        private const val SPLASH_SCREEN_DELAY = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        sharedPreferencesToken = getSharedPreferences("userToken", Context.MODE_PRIVATE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.visibility = View.INVISIBLE

        navController = Navigation.findNavController(this, R.id.fragment_container)

        setSplashScreen()

        setupWithNavController(binding.bottomNav, navController)
    }

    private fun setSplashScreen() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }, SPLASH_SCREEN_DELAY)

        splashScreenFragment = SplashFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, splashScreenFragment)
            .commit()

        val globalConfig = GlobalConfig()
        var greetingMessage = ""
        if (getTokenFromSharedPreferences() == "") {
            greetingMessage = globalConfig.getFirstGreetingMessage()
        }else {
            greetingMessage = globalConfig.getCommonGreetingMessage()
        }
        splashScreenFragment.setWelcomeMessageFromConfig(greetingMessage)

        Handler().postDelayed({
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MedicalFragment())
                .commit()
        }, SPLASH_SCREEN_DELAY)
    }
    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

}