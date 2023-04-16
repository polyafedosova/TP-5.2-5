package event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class AddEventFragment : Fragment() {

    private lateinit var cancelButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_add_event, container, false)

        cancelButton = view.findViewById(R.id.cancelEventAddButton)
        cancelButton.setOnClickListener() {
            it.findNavController().navigate(R.id.action_addEventFragment_to_eventsFragment)
        }

        return view
    }


}