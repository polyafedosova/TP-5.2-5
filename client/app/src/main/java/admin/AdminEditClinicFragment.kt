package admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import ru.vsu.cs.tp.paws.R


class AdminEditClinicFragment : Fragment() {

    private lateinit var adminEditClinicName: EditText
    private lateinit var adminEditClinicAddress: EditText
    private lateinit var adminEditClinicPhone: EditText
    private lateinit var adminEditClinicServices: EditText

    private lateinit var adminEditClinic: Button
    private lateinit var adminDeleteClinic: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_admin_edit_clinic, container, false)
    }


}