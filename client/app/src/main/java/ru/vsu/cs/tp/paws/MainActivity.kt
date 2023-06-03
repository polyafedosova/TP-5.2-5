package ru.vsu.cs.tp.paws

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import api.Api
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dto.OwnerDtoGet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.vsu.cs.tp.paws.databinding.ActivityMainBinding
import splash.SplashConfig
import splash.SplashFragment
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private lateinit var splashScreenFragment: SplashFragment

    private var flag = false

    private companion object {
        private const val SPLASH_SCREEN_DELAY = 5000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferencesToken = getSharedPreferences("userToken", Context.MODE_PRIVATE)
        sharedPreferencesLogin = getSharedPreferences("userLogin", Context.MODE_PRIVATE)

        getOnboardStatus()

        val config = YandexMetricaConfig.newConfigBuilder("93759b96-971e-411a-b343-c6d055d5e03b").build()
        YandexMetrica.activate(applicationContext, config)
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

    private fun firstStart() {
        binding.bottomNav.visibility = View.INVISIBLE
        navController.navigate(R.id.onboardFragmentFirst)
    }

    private fun setSplashScreen() {
        sharedPreferencesToken = getSharedPreferences("userToken", Context.MODE_PRIVATE)
        val globalConfig = SplashConfig()
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
                    val prefs = getPreferences(MODE_PRIVATE)
                    if (prefs.getBoolean("isFirstRun", true) || flag) {
                        firstStart()
                    } else {
                        navController.navigate(R.id.medicalFragment)
                        binding.bottomNav.visibility = View.VISIBLE
                    }
                    prefs.edit().putBoolean("isFirstRun", false).apply()

                }
            }
        }, SPLASH_SCREEN_DELAY)

    }
    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
    }

    fun getBottomNav(): BottomNavigationView {
        return binding.bottomNav
    }

    private fun getOnboardStatus() {
        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val call = Api.getApiOwner().findByLogin(getLoginFromSharedPreferences(), headers)

        call.enqueue(object : Callback<OwnerDtoGet> {
            override fun onResponse(call: Call<OwnerDtoGet>, response: Response<OwnerDtoGet>) {
                if (response.isSuccessful) {
                    val dataResponse = response.body()
                    runOnUiThread {
                        if (dataResponse != null) {
                            flag = dataResponse.show
                        }else{
                            flag = false
                        }
                    }
                } else {
                    flag = false
                }
            }

            override fun onFailure(call: Call<OwnerDtoGet>, t: Throwable) {
                runOnUiThread {
                    val alertDialogBuilder = AlertDialog.Builder(this@MainActivity)

                    alertDialogBuilder.setTitle("Внимание")
                    alertDialogBuilder.setMessage(
                        "Нет подключения к интернету, либо сервера недоступны. Повторите попытку позже.")

                    alertDialogBuilder.setNegativeButton("Ок") { dialogInterface: DialogInterface, i: Int ->
//                        val a: Int = "a".toInt()
                        this@MainActivity.finish()
                    }

                    alertDialogBuilder.create().show()
                }
                println(t)
            }
        })
    }

}