package onboard

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import interfaces.GlobalConfigInterface
import interfaces.OwnerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R


class OnboardFragmentFirst : Fragment() {

    private lateinit var onboartTextPlace: TextView
    private lateinit var onboardNextButton: FloatingActionButton
    private lateinit var imgOnboardPlace: ImageView

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://2.56.242.93:4000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var bitStr = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getGlobalConfig()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_onboard_first, container, false)

        onboartTextPlace = view.findViewById(R.id.onboartTextPlace)
        onboardNextButton = view.findViewById(R.id.onboardNextButton)
        imgOnboardPlace = view.findViewById(R.id.imgOnboardPlace)

        onboardNextButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboardFragmentFirst_to_onboardFragmentSecond)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onboartTextPlace
        try {
            convertBitStringToImage(bitStr, imgOnboardPlace)
        } catch (ex: Exception) { ex.stackTrace }

    }

    private fun getGlobalConfig() {
        val api = retrofit.create(GlobalConfigInterface::class.java)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.getAll().execute()
                if (response.isSuccessful) {
                    println(response.code())
                    println("res = " + response.body())
//                    bitStr = response.body()
                    requireActivity().runOnUiThread {
                        onboartTextPlace.text = response.body()?.get(0)?.text
                    }

                }

            }
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

    private fun convertBitStringToImage(bitString: String, imageView: ImageView) {
        val width = 200
        val height = bitString.length / width

        val pixels = IntArray(width * height)
        var pixelIndex = 0

        for (i in 0 until bitString.length step 8) {
            val byteString = bitString.substring(i, i + 8)
            val pixelValue = Integer.parseInt(byteString, 2)
            val pixelColor = Color.rgb(pixelValue, pixelValue, pixelValue)
            pixels[pixelIndex] = pixelColor
            pixelIndex++
        }

        val bitmap = Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
        imageView.setImageBitmap(bitmap)
    }

}