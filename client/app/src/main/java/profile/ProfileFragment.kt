package profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class ProfileFragment : Fragment() {

    private lateinit var addDogButton: Button
    private lateinit var editProfileButton: Button
    private lateinit var addEventsButton: Button
    private lateinit var exitProfileButton: Button

    fun newInstance(): ProfileFragment {
        return ProfileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        this.addDogButton = view.findViewById(R.id.addDogButton)
        this.editProfileButton = view.findViewById(R.id.editProfileButton)
        this.addEventsButton = view.findViewById(R.id.eventsButton)
        this.exitProfileButton = view.findViewById(R.id.exitButton)

        this.addDogButton.setOnClickListener {
//            Toast.makeText(this.context, "AAAA", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_profileFragment_to_dogAddFragment)
        }

        this.addEventsButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_eventsFragment)
        }

        this.editProfileButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        this.exitProfileButton.setOnClickListener {
            Toast.makeText(this.context, "GG", Toast.LENGTH_SHORT).show()
        }

        return view
    }



}