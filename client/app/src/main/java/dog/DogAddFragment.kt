package dog

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


class DogAddFragment : Fragment() {

    lateinit var dogName: EditText
    lateinit var dogBurnDate: EditText
    lateinit var breed: EditText

    private lateinit var completeButton: Button
    private lateinit var cancelButton: Button

    private var chosenSex = 0


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_dog_add, container, false)

        cancelButton = view.findViewById(R.id.cancelButton)
        completeButton = view.findViewById(R.id.completeButton)

        dogName = view.findViewById(R.id.dogName)
        dogBurnDate = view.findViewById(R.id.dogBurnDate)
        breed = view.findViewById(R.id.breed)

        val autoCompleteSex: AutoCompleteTextView = view.findViewById(R.id.sex)
        val sex = resources.getStringArray(R.array.sex_array)
        val arrayAdapterSex = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sex)
        autoCompleteSex.setAdapter(arrayAdapterSex)

        autoCompleteSex.setOnItemClickListener { adapterView, view, i, l ->
            chosenSex = adapterView.getItemIdAtPosition(i).toInt()
        }

        completeButton.setOnClickListener {
            if (validate(dogName, dogBurnDate, breed)) {
//                println(chosenSex)
            }
        }

        cancelButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
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