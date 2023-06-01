package ru.vsu.cs.tp.paws

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView


class SplashFragment : Fragment() {

    private lateinit var splashHello: TextView
    private lateinit var imgPlace: ImageView
    private var message = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        splashHello = view.findViewById(R.id.splashHello)
        imgPlace = view.findViewById(R.id.imgPlace)

        imgPlace.setImageResource(R.drawable.iccorgi)
        val welcomeMessage = getWelcomeMessageFromConfig()

        splashHello.text = welcomeMessage

        return view
    }

     fun setWelcomeMessageFromConfig(_message: String) {
         message = _message
    }

    private fun getWelcomeMessageFromConfig(): String {
        return message
    }

}