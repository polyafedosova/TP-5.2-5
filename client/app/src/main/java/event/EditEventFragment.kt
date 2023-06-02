package event

import android.content.Context
import android.content.SharedPreferences
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
import androidx.navigation.fragment.findNavController
import com.yandex.metrica.YandexMetrica
import dto.DogDtoPost
import dto.EventDtoPost
import interfaces.DogInterface
import interfaces.EventInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EditEventFragment : Fragment() {

    private lateinit var newEventName: EditText
    private lateinit var newEventDate: EditText
    private lateinit var newEventComment: EditText
    private lateinit var newEventTime: EditText

    private lateinit var completeEditEventButton: Button
    private lateinit var deleteEventButton: Button
    private lateinit var backFromEditEventButton: Button

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://2.56.242.93:4000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_edit_event, container, false)

        newEventName = view.findViewById(R.id.newEventName)
        newEventDate = view.findViewById(R.id.newEventDate)
        newEventComment = view.findViewById(R.id.newEventComment)
        newEventTime = view.findViewById(R.id.newEventTime)

        completeEditEventButton = view.findViewById(R.id.completeEditEventButton)
        deleteEventButton = view.findViewById(R.id.deleteEventButton)
        backFromEditEventButton = view.findViewById(R.id.backFromEditEventButton)

        val nameValue = requireArguments().getString("name")
        var dateValue = requireArguments().getString("date")
        val commentValue = requireArguments().getString("comment")

        dateValue = dateValue?.replace("-", ".")


        val format = DateTimeFormatter.ofPattern("yyyy.MM.dd")
        val parseDate = LocalDate.parse(dateValue, format)

        if (parseDate.monthValue < 10 && parseDate.dayOfMonth < 10) {
            dateValue = "0" + parseDate.dayOfMonth.toString() + "." + "0" + parseDate.monthValue.toString() +
                    "." + parseDate.year.toString()
        }
        if (parseDate.monthValue >= 10 && parseDate.dayOfMonth < 10) {
            dateValue = "0" + parseDate.dayOfMonth.toString() + "." + parseDate.monthValue.toString() +
                    "." + parseDate.year.toString()
        }
        if (parseDate.monthValue < 10 && parseDate.dayOfMonth >= 10) {
            dateValue = parseDate.dayOfMonth.toString() + "." + "0" + parseDate.monthValue.toString() +
                    "." + parseDate.year.toString()
        }
        if (parseDate.monthValue >= 10 && parseDate.dayOfMonth >= 10) {
            dateValue = parseDate.dayOfMonth.toString() + "." + parseDate.monthValue.toString() +
                    "." + parseDate.year.toString()
        }

        newEventName.setText(nameValue)
        newEventDate.setText(dateValue)
        newEventComment.setText(commentValue)

        completeEditEventButton.setOnClickListener() {
            if (validate(newEventName, newEventDate, newEventTime, newEventComment)) {
                updateEvent(newEventName, newEventDate, newEventTime, newEventComment)

            }

        }

        deleteEventButton.setOnClickListener() {
            deleteEvent()
        }

        backFromEditEventButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateEvent(newName: EditText, newDate: EditText, newTime: EditText, newComment: EditText) {
        val idValue = requireArguments().getInt("id")

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(EventInterface::class.java)

        val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        try {
            val modifiedTime = newTime.text.toString().replace("-", ":")

            var dateString = "1212-12-12"

            val parseDate = LocalDate.parse(newDate.text.toString(), format)

            if (parseDate.monthValue < 10 && parseDate.dayOfMonth < 10) {
                dateString = parseDate.year.toString() + "-" + "0" + parseDate.monthValue.toString() +
                        "-" + "0" + parseDate.dayOfMonth.toString()
            }
            if (parseDate.monthValue >= 10 && parseDate.dayOfMonth < 10) {
                dateString = parseDate.year.toString() + "-" + parseDate.monthValue.toString() +
                        "-" + "0" + parseDate.dayOfMonth.toString()
            }
            if (parseDate.monthValue < 10 && parseDate.dayOfMonth >= 10) {
                dateString = parseDate.year.toString() + "-" + "0" + parseDate.monthValue.toString() +
                        "-" + parseDate.dayOfMonth.toString()
            }
            if (parseDate.monthValue >= 10 && parseDate.dayOfMonth >= 10) {
                dateString = parseDate.year.toString() + "-" + parseDate.monthValue.toString() +
                        "-" + parseDate.dayOfMonth.toString()
            }

            val dto = EventDtoPost(newName.text.toString(), dateString, modifiedTime, newComment.text.toString())

            CoroutineScope(Dispatchers.IO).launch {
                val response = api.updateEvent(idValue, getLoginFromSharedPreferences(), dto, headers).execute()
                if (response.isSuccessful) {

                    println("L:D")
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }
                }else{
                    requireActivity().runOnUiThread {
                        when (response.code()) {
                            400 -> {
                                Toast.makeText(requireContext(), "Неверный логин",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    println(response.code())
                    println(response.message())
                }
            }
        } catch (e: java.lang.Exception) {
            println(e)
        }

    }

    private fun deleteEvent() {
        val idValue = requireArguments().getInt("id")

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(EventInterface::class.java)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.deleteEvent(idValue, getLoginFromSharedPreferences(), headers).execute()
                if (response.isSuccessful) {
                    println("L:D")
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                        YandexMetrica.reportEvent("Событие удалено")
                        findNavController().popBackStack()
                    }
                } else {
                    println(response.code())
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(),
                            "Ошибка сервера, повторите попытку позже", Toast.LENGTH_SHORT).show()
                    }
                    println("D:L")
                    println(response.message())
                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun validate(name: EditText, date: EditText, time: EditText, comment: EditText): Boolean {
        var isValid = true

        if (name.text.toString().isEmpty()) {
            name.error = "Введите название"
            isValid = false
        }

        if (date.text.toString().isEmpty()) {
            date.error = "Введите дату"
            isValid = false
        }

        if (time.text.toString().isEmpty()) {
            time.error = "Введите время"
            isValid = false
        }

        if (comment.text.toString().isEmpty()) {
            comment.setText("")
        }


        var parseDate: LocalDate
        val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        try {
            parseDate = LocalDate.parse(date.text.toString(), format)
        } catch (ex: java.lang.Exception) {
            date.error = "Ошибка в ведённой дате"
            isValid = false
            ex.stackTrace
        }

        return isValid
    }


    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
    }

}