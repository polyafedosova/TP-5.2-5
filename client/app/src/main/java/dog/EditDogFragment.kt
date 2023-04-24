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


class EditDogFragment : Fragment() {

    lateinit var newDogName: EditText
    lateinit var newDogBurnDate: EditText
    lateinit var newBreed: EditText

    private var chosenSex = -1

    lateinit var completeEditButton: Button
    lateinit var deleteDogButton: Button
    lateinit var backFromEditDogButton: Button


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_dog, container, false)

        newDogName = view.findViewById(R.id.newDogName)
        newDogBurnDate = view.findViewById(R.id.newDogBurnDate)
        newBreed = view.findViewById(R.id.newBreed)

//        val spinnerSex: Spinner = view.findViewById(R.id.newSex)

        val autoCompleteSex: AutoCompleteTextView = view.findViewById(R.id.newSex)
        val age = resources.getStringArray(R.array.sex_array)
        val arrayAdapterAge = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, age)
        autoCompleteSex.setAdapter(arrayAdapterAge)

        completeEditButton = view.findViewById(R.id.completeDogEditButton)
        deleteDogButton = view.findViewById(R.id.deleteDogButton)
        backFromEditDogButton = view.findViewById(R.id.backFromEditDogButton)




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

        autoCompleteSex.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                chosenSex = selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val nameValue = requireArguments().getString("name")
        val idValue = requireArguments().getInt("id")
        var dateValue = requireArguments().getString("data")
        val breedValue = requireArguments().getString("breed")

        dateValue = dateValue?.replace(" ", ".")

        newDogName.setText(nameValue)
        newDogBurnDate.setText(dateValue)
        newBreed.setText(breedValue)

        completeEditButton.setOnClickListener() {
            commitToServer(newDogName, newDogBurnDate, newBreed)
            it.findNavController().popBackStack()
        }

        deleteDogButton.setOnClickListener() {
            it.findNavController().popBackStack()
        }

        backFromEditDogButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun commitToServer(newName: EditText, newDate: EditText, newBreed: EditText) {
        Toast.makeText(this.requireContext(), "Как будто отправил на сервер", Toast.LENGTH_SHORT).show()
    }

}