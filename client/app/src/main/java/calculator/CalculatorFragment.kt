package calculator

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.fragment.app.Fragment
import com.yandex.metrica.YandexMetrica
import ru.vsu.cs.tp.paws.R


class CalculatorFragment : Fragment() {

    private lateinit var doneButton: Button
    private lateinit var ansField: TextView
    private lateinit var belkov: TextView
    private lateinit var rastitel: TextView

    private var chosenAge: Int = 0
    private var chosenMove: Int = 0
    private var weight = -1.0



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        YandexMetrica.reportEvent("Пользователь перешёл на экран калькулятора")

        val autoCompleteAge: AutoCompleteTextView = view.findViewById(R.id.age)
        val autoCompleteMove: AutoCompleteTextView = view.findViewById(R.id.movement)

        val age = resources.getStringArray(R.array.ages_array)
        val arrayAdapterAge = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, age)
        autoCompleteAge.setAdapter(arrayAdapterAge)

        autoCompleteAge.setOnItemClickListener { adapterView, view, i, l ->
            chosenAge = adapterView.getItemIdAtPosition(i).toInt()
            hideKeyboard()
        }

        autoCompleteAge.setOnClickListener {
            hideKeyboard()
        }

        autoCompleteMove.setOnClickListener {
            hideKeyboard()
        }

        val move = resources.getStringArray(R.array.move_array)
        val arrayAdapterMove = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, move)
        autoCompleteMove.setAdapter(arrayAdapterMove)

        autoCompleteMove.setOnItemClickListener { adapterView, view, i, l ->
            chosenMove = adapterView.getItemIdAtPosition(i).toInt()
        }

        doneButton = view.findViewById(R.id.calculate_eat)
        ansField = view.findViewById(R.id.mass2)
        belkov = view.findViewById(R.id.mass3)
        rastitel = view.findViewById(R.id.mass4)

        doneButton.setOnClickListener {
            try {
                weight = view.findViewById<EditText>(R.id.dogWeight).text.toString().toDouble()
                if (weight != 0.0) {
                    ansField.text = (calculateEat(chosenAge, chosenMove, weight) / 1000).toString()
                    belkov.text = (weight * 0.008 * 1000).toInt().toString()
                    rastitel.text = (ansField.text.toString().toDouble() * 0.07 * 1000).toInt().toString()
                }else {
                    Toast.makeText(this.context, "Неверно задана масса", Toast.LENGTH_SHORT).show()
                }

            } catch (ex: java.lang.Exception) {
                Toast.makeText(this.context, "Неверно задана масса", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun calculateEat(age: Int, move: Int, mass: Double): Double {

        var ageCoef: Double = 1.0
        var moveCoef: Double = 1.0
        when (age) {
            0 -> ageCoef = 3.2
            1 -> ageCoef = 2.0
            2 -> ageCoef = 1.0
        }

        when (move) {
            0 -> moveCoef = 0.8
            1 -> moveCoef = 2.0
            2 -> moveCoef = 3.2
        }
        return mass * 30 + ageCoef * 5 + moveCoef * 10
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}




