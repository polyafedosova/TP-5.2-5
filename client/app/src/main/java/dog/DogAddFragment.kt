package dog

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class DogAddFragment : Fragment() {

    lateinit var DogName: EditText
    lateinit var DogBurnDate: EditText
    lateinit var Breed: EditText

    private lateinit var completeButton: Button
    private lateinit var cancelButton: Button

    private var chosenSex = -1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_dog_add, container, false)

        this.cancelButton = view.findViewById(R.id.cancelButton)
        this.completeButton = view.findViewById(R.id.completeButton)

        val autoCompleteSex: AutoCompleteTextView = view.findViewById(R.id.sex)
        val age = resources.getStringArray(R.array.sex_array)
        val arrayAdapterAge = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, age)
        autoCompleteSex.setAdapter(arrayAdapterAge)


//        ArrayAdapter.createFromResource(this.requireContext(), R.array.sex_array,
//            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item).also {
//                adapter -> adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
//            spinnerSex.adapter = adapter
//        }
//
//        spinnerSex.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                itemSelected: View, selectedItemPosition: Int, selectedId: Long
//            ) {
//                chosenSex = selectedItemPosition
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        })

        autoCompleteSex.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                chosenSex = selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })


        this.cancelButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

}