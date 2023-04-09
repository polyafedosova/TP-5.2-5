package ru.vsu.cs.tp.paws

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


class CalculatorFragment : Fragment() {

    private lateinit var doneButton: Button
    private var chosenAge: String = ""
    private var chosenMove: String = ""
    private var weight = -1



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_calculator, container, false)

        val spinnerAge: Spinner = view.findViewById(R.id.age)

        val spinnerMove: Spinner = view.findViewById(R.id.movement)

        ArrayAdapter.createFromResource(this.requireContext(), R.array.ages_array,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item).also {
                adapter -> adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
            spinnerAge.adapter = adapter
        }


        ArrayAdapter.createFromResource(this.requireContext(), R.array.move_array,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item).also {
                    adapter -> adapter.setDropDownViewResource(androidx.transition.R.layout.support_simple_spinner_dropdown_item)
            spinnerMove.adapter = adapter
        }

        spinnerMove.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                chosenMove = resources.getStringArray(R.array.move_array)[selectedItemPosition].toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        spinnerAge.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                chosenAge = resources.getStringArray(R.array.ages_array)[selectedItemPosition].toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })

        this.doneButton = view.findViewById(R.id.calculate_eat)


        this.doneButton.setOnClickListener {

            try {
                weight = Integer.valueOf(view.findViewById<EditText>(R.id.dogWeight).text.toString())
                calculateEat(chosenAge, chosenMove, weight)
            } catch (ex: java.lang.Exception) {
                Toast.makeText(this.context, "Что-то сломалось, попробуйте ещё раз", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun calculateEat(age: String, move: String, mass: Int) {
        println(age)
        println(move)
        println(mass)
    }

}




