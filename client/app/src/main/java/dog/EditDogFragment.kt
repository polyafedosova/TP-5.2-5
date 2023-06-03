package dog

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
import com.yandex.metrica.YandexMetrica
import dto.DogDtoPost
import interfaces.DogInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class EditDogFragment : Fragment() {

    lateinit var newDogName: EditText
    lateinit var newDogBurnDate: EditText
    lateinit var newBreed: EditText

    private var chosenSex = 1

    private lateinit var sharedPreferencesToken: SharedPreferences
    private lateinit var sharedPreferencesLogin: SharedPreferences

    lateinit var completeEditButton: Button
    lateinit var deleteDogButton: Button
    lateinit var backFromEditDogButton: Button

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
        val view = inflater.inflate(R.layout.fragment_edit_dog, container, false)

        newDogName = view.findViewById(R.id.newDogName)
        newDogBurnDate = view.findViewById(R.id.newDogBurnDate)
        newBreed = view.findViewById(R.id.newBreed)

        completeEditButton = view.findViewById(R.id.completeDogEditButton)
        deleteDogButton = view.findViewById(R.id.deleteDogButton)
        backFromEditDogButton = view.findViewById(R.id.backFromEditDogButton)

        val newSex: AutoCompleteTextView = view.findViewById(R.id.newSex)
        val sex = resources.getStringArray(R.array.sex_array)
        val arrayAdapterSex = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, sex)
        newSex.setAdapter(arrayAdapterSex)

        newSex.setOnItemClickListener { adapterView, view, i, l ->
            chosenSex = adapterView.getItemIdAtPosition(i).toInt()
        }

        val nameValue = requireArguments().getString("name")
        var dateValue = requireArguments().getString("date")
        val breedValue = requireArguments().getString("breed")
        val sexValue = requireArguments().getBoolean("sex")

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

        newDogName.setText(nameValue)
        newDogBurnDate.setText(dateValue)
        newBreed.setText(breedValue)
        println(sexValue)
        if (!sexValue) {
            newSex.setText("")
        } else {
            newSex.setText("")
        }

        completeEditButton.setOnClickListener() {

            if (validate(newDogName, newDogBurnDate, newBreed)) {
                updateDog(newDogName, newDogBurnDate, newBreed, chosenSex)

            }

        }

        deleteDogButton.setOnClickListener() {
            deleteDog()
        }

        backFromEditDogButton.setOnClickListener {
            it.findNavController().popBackStack()
        }

        return view
    }

    private fun deleteDog() {
        val idValue = requireArguments().getInt("id")

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(DogInterface::class.java)

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val response = api.deleteDog(idValue, getLoginFromSharedPreferences(), headers).execute()
                if (response.isSuccessful) {
                    println("L:D")
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        YandexMetrica.reportEvent("Собака удалена")
                    }
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(),
                            "Ошибка сервера, повторите попытку позже", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        } catch (ex: Exception) {
            println(ex)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDog(newName: EditText, newDate: EditText, newBreed: EditText, sex: Int) {
        var ansSex = true
        if (sex == 0) {
            ansSex = false
        }

        val idValue = requireArguments().getInt("id")

        val token = getTokenFromSharedPreferences()
        val headers = HashMap<String, String>()
        headers["Authorization"] = "Bearer $token"

        val api = retrofit.create(DogInterface::class.java)

        val format = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        try {
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

            val dto = DogDtoPost(newName.text.toString(), dateString, ansSex, newBreed.text.toString())

            CoroutineScope(Dispatchers.IO).launch {
                val response = api.updateDog(idValue, dto, getLoginFromSharedPreferences(), headers).execute()
                if (response.isSuccessful) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Успешно", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                    }

                }else{
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(),
                            "В полях ввода используйте русский язык", Toast.LENGTH_SHORT).show()
                    }
                    println(response.code())
                    println("D:L")
                    println(response.message())
                }
            }
        } catch (e: java.lang.Exception) {
            println(e)
        }

    }

    private fun getTokenFromSharedPreferences(): String {
        return sharedPreferencesToken.getString("token", "") ?: ""
    }

    private fun getLoginFromSharedPreferences(): String {
        return sharedPreferencesLogin.getString("login", "") ?: ""
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

        } catch (ex: java.lang.Exception) {
            date.error = "Ошибка в ведённой дате"
            isValid = false
            ex.stackTrace
        }

        return isValid
    }

}