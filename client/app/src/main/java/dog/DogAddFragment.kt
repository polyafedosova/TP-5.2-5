package dog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class DogAddFragment : Fragment() {

    private lateinit var completeButton: Button
    private lateinit var cancelButton: Button

    private var chosenSex = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_dog_add, container, false)

        this.cancelButton = view.findViewById(R.id.cancelButton)
        this.completeButton = view.findViewById(R.id.completeButton)

        val spinnerSex: Spinner = view.findViewById(R.id.sex)

        ArrayAdapter.createFromResource(this.requireContext(), R.array.sex_array,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item).also {
                adapter -> adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
            spinnerSex.adapter = adapter
        }

        spinnerSex.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                chosenSex = selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        this.cancelButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_dogAddFragment_to_profileFragment)
        }

        return view
    }

}