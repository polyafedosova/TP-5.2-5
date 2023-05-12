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


class AddEventFragment : Fragment() {

    private lateinit var completeAddEventButton: Button
    private lateinit var cancelButton: Button
    private lateinit var eventName: EditText
    private lateinit var eventDate: EditText
    private lateinit var eventComment: EditText

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_add_event, container, false)

        completeAddEventButton = view.findViewById(R.id.completeAddEventButton)
        cancelButton = view.findViewById(R.id.cancelEventAddButton)

        eventName = view.findViewById(R.id.eventName)
        eventDate = view.findViewById(R.id.eventDate)
        eventComment = view.findViewById(R.id.eventComment)

        completeAddEventButton.setOnClickListener() {
            if (validate(eventName, eventDate, eventComment)) {
                //отправка
                it.findNavController().popBackStack()
            }


        }

        cancelButton.setOnClickListener() {
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