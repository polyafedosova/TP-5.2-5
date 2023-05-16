package profile

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import auth.LoginFragment
import auth.TokenManager
import dog.DogAdapter
import dog.DogModel
import ru.vsu.cs.tp.paws.R
import java.time.LocalDate


class ProfileFragment : Fragment() {

    private lateinit var addDogButton: Button
    private lateinit var editProfileButton: Button
    private lateinit var addEventsButton: Button
    private lateinit var exitProfileButton: Button

    private lateinit var recyclerView: RecyclerView
    private lateinit var eventsAdapter: DogAdapter

    private lateinit var tokenManager: TokenManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenManager = TokenManager(requireContext())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        var view: View? = null

        if (!tokenManager.isTokenValid()) {
            startLoginFragment()
        }

//        if (isAuthorized()) {
            val viewTe = inflater.inflate(R.layout.fragment_profile, container, false)

            recyclerView = viewTe.findViewById(R.id.recyclerDogs)
            recyclerView.layoutManager = LinearLayoutManager(activity)

            eventsAdapter = DogAdapter(getDataDogs() as MutableList<DogModel>)
            recyclerView.adapter = eventsAdapter

            this.addDogButton = viewTe.findViewById(R.id.addDogButton)
            this.editProfileButton = viewTe.findViewById(R.id.editProfileButton)
            this.addEventsButton = viewTe.findViewById(R.id.eventsButton)
            this.exitProfileButton = viewTe.findViewById(R.id.exitButton)

            addDogButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_profileFragment_to_dogAddFragment)
            }

            addEventsButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_profileFragment_to_eventsFragment)
            }

            editProfileButton.setOnClickListener {
                it.findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }

            exitProfileButton.setOnClickListener {
                Toast.makeText(this.context, "GG", Toast.LENGTH_SHORT).show()
                //logout
                it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }

            return viewTe
//        } else {
//            view = LoginFragment().onCreateView(inflater, container, savedInstanceState)
//        }

//        return view
    }

    private fun startLoginFragment() {
        val loginFragment = LoginFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    //временные костыли
    private fun isAuthorized(): Boolean {
        return false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDataDogs(): List<DogModel> {
        val listDogs: MutableList<DogModel> = java.util.ArrayList()
        val date: LocalDate = LocalDate.of(2015, 7,24)
        val dateString = date.year.toString()+ " " + date.month.value.toString() + " " + date.dayOfMonth.toString()

        listDogs.add(DogModel(1,"Собака 1", dateString, "Порода 1"))
        listDogs.add(DogModel(2,"Собака 2", dateString, "Порода 2"))

        return listDogs
    }

}