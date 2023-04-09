package ru.vsu.cs.tp.paws

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

//import ru.vsu.cs.tp.paws.databinding.FragmentCalculatorBinding

class CalculatorFragment : Fragment() {

    var age = arrayOf("Молодая", "Средний", "Старая")

//    private lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        binding = DataBindingUtil.inflate<FragmentCalculatorBinding>(inflater, R.layout.fragment_calculator, container, false)

        var spinner = R.id.age

        return inflater.inflate(R.layout.fragment_calculator, container, false)
    }

}




