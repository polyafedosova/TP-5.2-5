package onboard

import android.content.Context
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import interfaces.GlobalConfigInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.MainActivity
import ru.vsu.cs.tp.paws.R


class OnboardFragmentSecond : Fragment() {

    private lateinit var onboartTextPlace2: TextView
    private lateinit var onboardCompleteButton: Button
    private lateinit var imgOnboardPlace2: ImageView

    private lateinit var sharedPreferencesLogin: SharedPreferences
    private lateinit var sharedPreferencesToken: SharedPreferences

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://2.56.242.93:4000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
        getGlobalConfig()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_onboard_second, container, false)

        onboartTextPlace2 = view.findViewById(R.id.onboartTextPlace2)
        onboardCompleteButton = view.findViewById(R.id.onboardCompleteButton)
        imgOnboardPlace2 = view.findViewById(R.id.imgOnboardPlace2)

        Glide.with(this).asGif().load(R.drawable.dog_hello).into(imgOnboardPlace2)

        val mainActivity = activity as MainActivity

        onboardCompleteButton.setOnClickListener {
            mainActivity.getBottomNav().visibility = View.VISIBLE
            changeFlag()
            findNavController().navigate(R.id.action_onboardFragmentSecond_to_medicalFragment)
        }

        return view
    }

    private fun changeFlag() {
        val api = retrofit.create(GlobalConfigInterface::class.java)
        println("token - " + getTokenFromSharedPreferences())
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer ${getTokenFromSharedPreferences()}"

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.flagToFalse(getLoginFromSharedPreferences(), false, headers).execute()
                if (response.isSuccessful) {
                    println("flag code - " + response.code())
                }else {
                    println("flag code - " + response.code())
                }

            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    private fun getGlobalConfig() {
        val api = retrofit.create(GlobalConfigInterface::class.java)
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.getAll().execute()
                if (response.isSuccessful) {
                    requireActivity().runOnUiThread {
                        onboartTextPlace2.text = response.body()?.get(1)?.text
                    }

                }

            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
    }

    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

}