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

        val eventNameText = eventName.text
        val eventDateText = eventDate.text
        val eventCommentText = eventComment.text


        completeAddEventButton.setOnClickListener() {
//            println("---------------------------")
//            println(eventDateText)
//            println("---------------------------")

            var flag = 0
            var message = "Ошибка в ведённой дате"
            var format = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            try {
                val date: LocalDate = LocalDate.parse(eventDateText, format)

                val dateString = date.year.toString()+ " " + date.month.value.toString() + " " + date.dayOfMonth.toString()

//                println("---------------------------")
//                println(dateString)
//                println("---------------------------")
            } catch (ex: java.lang.Exception) {
                println(ex)
                flag = 1
            }

            if (flag == 1) {
                Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
            }

        }

        cancelButton.setOnClickListener() {
            it.findNavController().popBackStack()
        }

        return view
    }


}