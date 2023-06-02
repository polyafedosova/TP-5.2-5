package ru.vsu.cs.tp.paws

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import ru.vsu.cs.tp.paws.databinding.ActivityMainBinding
import java.util.*

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

        val config = YandexMetricaConfig.newConfigBuilder("93759b96-971e-411a-b343-c6d055d5e03b").build()
        YandexMetrica.activate(getApplicationContext(), config)
        YandexMetrica.enableActivityAutoTracking(application)

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNav.visibility = View.INVISIBLE

        navController = Navigation.findNavController(this, R.id.fragment_container)

        setSplashScreen()

        setupWithNavController(binding.bottomNav, navController)
    }

    private fun setSplashScreen() {
        sharedPreferencesToken = getSharedPreferences("userToken", Context.MODE_PRIVATE)
        val globalConfig = GlobalConfig()
        var greetingMessage = ""
        if (getTokenFromSharedPreferences() == "") {
            greetingMessage = globalConfig.getFirstGreetingMessage()
        }else {
            greetingMessage = globalConfig.getCommonGreetingMessage()
        }
        splashScreenFragment = SplashFragment()
        val bundle = Bundle()
        bundle.putString("message", greetingMessage)
        navController.navigate(R.id.splashFragment, bundle)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    navController.navigate(R.id.medicalFragment)
                    binding.bottomNav.visibility = View.VISIBLE
                }
            }
        }, SPLASH_SCREEN_DELAY)


    }
    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

}