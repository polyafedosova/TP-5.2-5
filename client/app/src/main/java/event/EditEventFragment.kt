package event

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class EditEventFragment : Fragment() {

    lateinit var newEventName: EditText
    lateinit var newEventDate: EditText
    lateinit var newEventComment: EditText

    lateinit var completeEditEventButton: Button
    lateinit var deleteEventButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_event, container, false)

        newEventName = view.findViewById(R.id.newEventName)
        newEventDate = view.findViewById(R.id.newEventDate)
        newEventComment = view.findViewById(R.id.newEventComment)

        completeEditEventButton = view.findViewById(R.id.completeEditEventButton)
        deleteEventButton = view.findViewById(R.id.deleteEventButton)

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

            commitToServer(newEventName, newEventDate, newEventComment)
            it.findNavController().popBackStack()
        }

        deleteEventButton.setOnClickListener() {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun commitToServer(newName: EditText, newDate: EditText, newComment: EditText) {
        Toast.makeText(this.requireContext(), "Как будто отправил на сервер", Toast.LENGTH_SHORT).show()
    }

}