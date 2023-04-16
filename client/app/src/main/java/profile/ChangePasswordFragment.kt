package profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class ChangePasswordFragment : Fragment() {

    private lateinit var completeChangePasswordButton: Button
    private lateinit var cancelChangePasswordButton: Button

    private lateinit var newPassword: EditText
    private lateinit var repeatNewPassword: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_change_password, container, false)

        completeChangePasswordButton = view.findViewById(R.id.completeChangePasswordButton)
        cancelChangePasswordButton = view.findViewById(R.id.cancelChangePasswordButton)

        completeChangePasswordButton.setOnClickListener {

        }

        cancelChangePasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_changePasswordFragment_to_editProfileFragment)
        }

        return view
    }

}