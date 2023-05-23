package dog

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dto.DogDtoPost
import dto.OwnerDtoGet
import interfaces.DogInterface
import interfaces.OwnerInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date


class DogAddFragment : Fragment() {

    lateinit var dogName: EditText
    lateinit var dogBurnDate: EditText
    lateinit var breed: EditText

    private lateinit var completeButton: Button
    private lateinit var cancelButton: Button

    private var chosenSex = 1

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesLogin = requireActivity().getSharedPreferences("userLogin", Context.MODE_PRIVATE)
        sharedPreferencesToken = requireActivity().getSharedPreferences("userToken", Context.MODE_PRIVATE)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_dog_add, container, false)

        println("==========================")
        println(getLoginFromSharedPreferences())
        println(getTokenFromSharedPreferences())

        cancelButton = view.findViewById(R.id.cancelButton)
        completeButton = view.findViewById(R.id.completeButton)

        dogName = view.findViewById(R.id.dogName)
        dogBurnDate = view.findViewById(R.id.dogBurnDate)
        breed = view.findViewById(R.id.breed)

        val autoCompleteSex: AutoCompleteTextView = view.findViewById(R.id.sex)
        val sex = resources.getStringArray(R.array.sex_array)
        val arrayAdapterSex = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sex)
        autoCompleteSex.setAdapter(arrayAdapterSex)

        autoCompleteSex.setOnItemClickListener { adapterView, view, i, l ->
            chosenSex = adapterView.getItemIdAtPosition(i).toInt()
            println(chosenSex)
        }

        completeButton.setOnClickListener {
            println("chose - " + chosenSex)
            if (validate(dogName, dogBurnDate, breed)) {

                addDog(dogName, dogBurnDate, breed, chosenSex)

            }
        }

        cancelButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addDog(name: EditText, birthday: EditText, breed: EditText, sex: Int) {
        var ansSex = true
        if (sex == 0) {
                ansSex = false
        }

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(DogInterface::class.java)

        val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        try {
            var dateString = "1212-12-12"

            val parseDate = LocalDate.parse(birthday.text.toString(), format)

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

            val dto = DogDtoPost(name.text.toString(), dateString, ansSex, breed.text.toString())

            CoroutineScope(Dispatchers.IO).launch {
                val response = api.saveNewDog(getLoginFromSharedPreferences(), dto, headers).execute()
                if (response.isSuccessful) {

                    println("L:D")
                    requireActivity().runOnUiThread {
                        findNavController().popBackStack()
                    }
                }else{
                    println(response.code())

                    println("D:L")
                    println(response.message())
                }
            }
        } catch (e: java.lang.Exception) {
            println(e)
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun validate(name: EditText, date: EditText, breed: EditText): Boolean {
        var isValid = true

        if (name.text.toString().isEmpty()) {
            name.error = "Введите название"
            isValid = false
        }

        if (date.text.toString().isEmpty()) {
            date.error = "Введите дату"
            isValid = false
        }

        if (breed.text.toString().isEmpty()) {
            breed.error = "Введите породу"
            isValid = false
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