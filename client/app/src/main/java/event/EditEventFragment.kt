package event

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EditEventFragment : Fragment() {

    private lateinit var newEventName: EditText
    private lateinit var newEventDate: EditText
    private lateinit var newEventComment: EditText

    private lateinit var completeEditEventButton: Button
    private lateinit var deleteEventButton: Button
    private lateinit var backFromEditEventButton: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_event, container, false)

        newEventName = view.findViewById(R.id.newEventName)
        newEventDate = view.findViewById(R.id.newEventDate)
        newEventComment = view.findViewById(R.id.newEventComment)

        completeEditEventButton = view.findViewById(R.id.completeEditEventButton)
        deleteEventButton = view.findViewById(R.id.deleteEventButton)
        backFromEditEventButton = view.findViewById(R.id.backFromEditEventButton)

        val nameValue = requireArguments().getString("name")
        val idValue = requireArguments().getInt("id")
        var dateValue = requireArguments().getString("data")
        val commentValue = requireArguments().getString("comment")

//        Toast.makeText(this.requireContext(), nameValue, Toast.LENGTH_SHORT).show()

        dateValue = dateValue?.replace(" ", ".")

        newEventName.setText(nameValue)
        newEventDate.setText(dateValue)
        newEventComment.setText(commentValue)

        completeEditEventButton.setOnClickListener() {

            validate(newEventName, newEventDate, newEventComment)
            it.findNavController().popBackStack()
        }

        deleteEventButton.setOnClickListener() {
            it.findNavController().popBackStack()
        }

        backFromEditEventButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun validate(name: EditText, date: EditText, comment: EditText): Boolean {
        var isValid = true

        if (name.text.toString().isEmpty()) {
            name.error = "Введите название"
            isValid = false
        }

        if (date.text.toString().isEmpty()) {
            date.error = "Введите дату"
            isValid = false
        }

        if (comment.text.toString().isEmpty()) {
            comment.setText("")
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