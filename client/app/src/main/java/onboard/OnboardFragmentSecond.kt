package onboard

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
import ru.vsu.cs.tp.paws.MainActivity
import ru.vsu.cs.tp.paws.R


class OnboardFragmentSecond : Fragment() {

    private lateinit var onboartTextPlace2: TextView
    private lateinit var onboardCompleteButton: Button
    private lateinit var imgOnboardPlace2: ImageView

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
            findNavController().navigate(R.id.action_onboardFragmentSecond_to_medicalFragment)
        }

        return view
    }


}