package dog

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EditDogFragment : Fragment() {

    lateinit var newDogName: EditText
    lateinit var newDogBurnDate: EditText
    lateinit var newBreed: EditText

    private var chosenSex = 0

    lateinit var completeEditButton: Button
    lateinit var deleteDogButton: Button
    lateinit var backFromEditDogButton: Button


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_dog, container, false)

        newDogName = view.findViewById(R.id.newDogName)
        newDogBurnDate = view.findViewById(R.id.newDogBurnDate)
        newBreed = view.findViewById(R.id.newBreed)

        completeEditButton = view.findViewById(R.id.completeDogEditButton)
        deleteDogButton = view.findViewById(R.id.deleteDogButton)
        backFromEditDogButton = view.findViewById(R.id.backFromEditDogButton)

        val autoCompleteSex: AutoCompleteTextView = view.findViewById(R.id.newSex)
        val sex = resources.getStringArray(R.array.sex_array)
        val arrayAdapterSex = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sex)
        autoCompleteSex.setAdapter(arrayAdapterSex)

        autoCompleteSex.setOnItemClickListener { adapterView, view, i, l ->
            chosenSex = adapterView.getItemIdAtPosition(i).toInt()
        }

        val nameValue = requireArguments().getString("name")
        val idValue = requireArguments().getInt("id")
        var dateValue = requireArguments().getString("date")
        val breedValue = requireArguments().getString("breed")

        dateValue = dateValue?.replace("-", ".")
        println("date - " + dateValue)

        newDogName.setText(nameValue)
        newDogBurnDate.setText(dateValue)
        newBreed.setText(breedValue)

        completeEditButton.setOnClickListener() {
            if (validate(newDogName, newDogBurnDate, newBreed)) {
                it.findNavController().popBackStack()
            }

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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validate(name: EditText, date: EditText, breed: EditText): Boolean {
        var isValid = true

        if (name.text.toString().isEmpty()) {
            name.error = "Введите название"
            isValid = false
        }

        if (date.text.toString().isEmpty()) {
            date.error = "Введите дату"
            isValid = false
        }

        if (breed.text.toString().isEmpty()) {
            breed.error = "Введите породу"
            isValid = false
        }

        var parseDate: LocalDate
        val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        try {
            parseDate = LocalDate.parse(date.text.toString(), format)

//            val dateString = parseDate.year.toString()+ " " + parseDate.month.value.toString() +
//                    " " + parseDate.dayOfMonth.toString()
        } catch (ex: java.lang.Exception) {
            date.error = "Ошибка в ведённой дате"
            isValid = false
            ex.stackTrace
        }

        return isValid
    }

}