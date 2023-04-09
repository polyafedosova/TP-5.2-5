package Profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class ProfileFragment : Fragment() {

    private lateinit var editButton: Button

    fun newInstance(): ProfileFragment {
        return ProfileFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        val view: View = inflater.inflate(R.layout.fragment_profile, container, false)

        editButton = view.findViewById(R.id.editProfileButton)



        editButton.setOnClickListener {
//            Toast.makeText(this.context, "AAAA", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_profileFragment_to_dogAddFragment)
        }

        return view
    }



}