package event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ru.vsu.cs.tp.paws.R


class SpecificEventFragment : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val nameValue = requireArguments().getString("name")

        Toast.makeText(this.requireContext(), nameValue, Toast.LENGTH_SHORT).show()

        return inflater.inflate(R.layout.fragment_specific_event, container, false)
    }


}