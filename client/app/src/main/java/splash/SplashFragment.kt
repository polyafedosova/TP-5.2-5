package splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.vsu.cs.tp.paws.MainActivity
import ru.vsu.cs.tp.paws.R


class SplashFragment : Fragment() {

    private lateinit var splashHello: TextView
    private lateinit var imgPlace: ImageView
    private var message = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        splashHello = view.findViewById(R.id.splashHello)
        imgPlace = view.findViewById(R.id.imgPlace)

        imgPlace.setImageResource(R.drawable.iccorgi)
        val welcomeMessage = requireArguments().getString("message")
        splashHello.text = welcomeMessage

        return view
    }

     fun setWelcomeMessageFromConfig(_message: String) {
         message = _message
    }


}