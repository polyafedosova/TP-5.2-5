package event

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import dto.EventDtoPost
import interfaces.EventInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class AddEventFragment : Fragment() {

    private lateinit var completeAddEventButton: Button
    private lateinit var cancelButton: Button
    private lateinit var eventName: EditText
    private lateinit var eventDate: EditText
    private lateinit var eventComment: EditText
    private lateinit var eventTime: EditText

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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view: View = inflater.inflate(R.layout.fragment_add_event, container, false)

        completeAddEventButton = view.findViewById(R.id.completeAddEventButton)
        cancelButton = view.findViewById(R.id.cancelEventAddButton)

        eventName = view.findViewById(R.id.eventName)
        eventDate = view.findViewById(R.id.eventDate)
        eventTime = view.findViewById(R.id.eventTime)
        eventComment = view.findViewById(R.id.eventComment)

        eventDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ничего не делаем перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Ничего не делаем при изменении текста
            }

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                val length = input.length

                if (length == 3 || length == 6) {
                    if (input[length - 1] != '.') {
                        val newText = StringBuilder(input)
                        newText.insert(length - 1, '.')
                        eventDate.setText(newText)
                        eventDate.setSelection(newText.length)
                    }
                }
            }
        })

        eventTime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Ничего не делаем перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Ничего не делаем при изменении текста
            }

            override fun afterTextChanged(s: Editable?) {
                val input = s.toString()
                val length = input.length

                if (length == 3 || length == 6) {
                    if (input[length - 1] != ':') {
                        val newText = StringBuilder(input)
                        newText.insert(length - 1, ':')
                        eventTime.setText(newText)
                        eventTime.setSelection(newText.length)
                    }
                }
            }
        })



        completeAddEventButton.setOnClickListener() {
            if (validate(eventName, eventDate, eventTime, eventComment)) {
                addEvent(eventName, eventDate, eventTime, eventComment)
            }

        }

        cancelButton.setOnClickListener() {
            it.findNavController().navigate(R.id.eventsFragment)
        }

        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addEvent(name: EditText, date: EditText, time: EditText, comment: EditText) {
        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(EventInterface::class.java)

        val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        try {
            val modifiedTime = time.text.toString().replace("-", ":")

            var dateString = "1212-12-12"

            val parseDate = LocalDate.parse(date.text.toString(), format)

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
            val dto = EventDtoPost(name.text.toString(), dateString, modifiedTime, comment.text.toString())
            println(dto)
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.saveNewEvent(getLoginFromSharedPreferences(), dto, headers).execute()
                if (response.isSuccessful) {

                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                        YandexMetrica.reportEvent("Событие добавлено")
                        findNavController().navigate(R.id.action_addEventFragment_to_eventsFragment)
                    }
                } else{
                    requireActivity().runOnUiThread {
                        when (response.code()) {
                            400 -> {
                                Toast.makeText(requireContext(), "Ошибка в дате/времени",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
            }
        } catch (e: java.lang.Exception) {
            println(e)
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