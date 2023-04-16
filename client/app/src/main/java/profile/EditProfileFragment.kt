package profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
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

        this.completeProfileRenameButton = view.findViewById(R.id.completeProfileRenameButton)
        this.changePasswordButton = view.findViewById(R.id.changePasswordButton)
        this.cancelProfileRenameButton = view.findViewById(R.id.cancelProfileRenameButton)

        completeProfileRenameButton.setOnClickListener {

        }

        changePasswordButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_editProfileFragment_to_changePasswordFragment)
        }

        cancelProfileRenameButton.setOnClickListener {
            it.findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

        return view
    }


}