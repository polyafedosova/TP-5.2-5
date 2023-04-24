package calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import ru.vsu.cs.tp.paws.R


class CalculatorFragment : Fragment() {

    private lateinit var doneButton: Button
    private lateinit var ansField: TextView
    private lateinit var textAnswer: TextView

    private var chosenAge: Int = -1
    private var chosenMove: Int = -1
    private var weight = -1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        val autoCompleteAge: AutoCompleteTextView = view.findViewById(R.id.age)
        val autoCompleteMove: AutoCompleteTextView = view.findViewById(R.id.movement)

        val age = resources.getStringArray(R.array.ages_array)
        val arrayAdapterAge = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, age)
        autoCompleteAge.setAdapter(arrayAdapterAge)


        val move = resources.getStringArray(R.array.move_array)
        val arrayAdapterMove = ArrayAdapter(requireContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, move)
        autoCompleteMove.setAdapter(arrayAdapterMove)

        autoCompleteMove.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                //                chosenMove = resources.getStringArray(R.array.move_array)[selectedItemPosition].toString()
                chosenMove = selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        autoCompleteAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                chosenAge = selectedItemPosition
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        this.doneButton = view.findViewById(R.id.calculate_eat)
        this.ansField = view.findViewById(R.id.mass2)
        this.textAnswer = view.findViewById(R.id.info3)

        this.doneButton.setOnClickListener {

            try {
                weight = Integer.valueOf(view.findViewById<EditText>(R.id.dogWeight).text.toString())
                ansField.text = calculateEat(chosenAge, chosenMove, weight).toString()
                textAnswer.visibility = View.VISIBLE
            } catch (ex: java.lang.Exception) {
                Toast.makeText(this.context, "Что-то сломалось, попробуйте ещё раз", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun calculateEat(age: Int, move: Int, mass: Int): Double {
        println(age)
        println(move)
        println(mass)
        var ageCoef: Double = 1.0
        var moveCoef: Double = 1.0
        when (age) {
            0 -> ageCoef = 1.8
            1 -> ageCoef = 1.5
            2 -> ageCoef = 1.0
        }

        when (move) {
            0 -> moveCoef = 0.8
            1 -> moveCoef = 1.5
            2 -> moveCoef = 2.0
        }

        return mass * 30 + ageCoef * 5 + moveCoef * 10
    }

}




