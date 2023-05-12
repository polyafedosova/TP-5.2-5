package profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import ru.vsu.cs.tp.paws.R


class EditProfileFragment : Fragment() {

    private lateinit var completeProfileRenameButton: Button
    private lateinit var changePasswordButton: Button
    private lateinit var cancelProfileRenameButton: Button

    private lateinit var newLogin: EditText
    private lateinit var newName: EditText


    @SuppressLint("RestrictedApi")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        completeProfileRenameButton = view.findViewById(R.id.completeProfileRenameButton)
        changePasswordButton = view.findViewById(R.id.changePasswordButton)
        cancelProfileRenameButton = view.findViewById(R.id.cancelProfileRenameButton)

        newLogin = view.findViewById(R.id.newLogin)
        newName = view.findViewById(R.id.newName)

        completeProfileRenameButton.setOnClickListener {
            validateFieldsAndUpdate(newLogin, newName)
        }

        changePasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_editProfileFragment_to_changePasswordFragment)
        }

        cancelProfileRenameButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun validateFieldsAndUpdate(login: EditText, name: EditText) {
        var isValid = true
        if (login.text.toString().isEmpty() && name.text.toString().isEmpty()) {
            login.error = "Заполните хотябы одно поле"
            name.error = "Заполните хотябы одно поле"
            isValid = false
        }

        if (isValid && login.text.toString().isEmpty() && name.text.toString().isNotEmpty()) {

        }

        if (isValid && name.text.toString().isEmpty() && login.text.toString().isNotEmpty()) {
            //проверка на индивидуальность логина
        }

        if (isValid && name.text.toString().isNotEmpty() && login.text.toString().isNotEmpty()) {
            //проверка на индивидуальность логина
        }

    }

}