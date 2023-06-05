package onboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import interfaces.GlobalConfigInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R


class OnboardFragmentSecond : Fragment() {

    private lateinit var onboartTextPlace: TextView
    private lateinit var onboardNextButton: FloatingActionButton
    private lateinit var imgOnboardPlace: ImageView
    private lateinit var title: TextView

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://2.56.242.93:4000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getGlobalConfig()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_onboard_second, container, false)

        title = view.findViewById(R.id.title)
        onboartTextPlace = view.findViewById(R.id.onboartTextPlace2)
        onboardNextButton = view.findViewById(R.id.onboardNextButton)
        imgOnboardPlace = view.findViewById(R.id.imgOnboardPlace2)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)

        onboardNextButton.setOnClickListener {
            viewPager?.currentItem = 2
        }
        return view
    }

    private fun getGlobalConfig() {
        val api = retrofit.create(GlobalConfigInterface::class.java)
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.getAll().execute()
                if (response.isSuccessful) {
                    requireActivity().runOnUiThread {
                        title.text = response.body()?.get(1)?.title
                        onboartTextPlace.text = response.body()?.get(1)?.text
                    }
                }
            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }


}