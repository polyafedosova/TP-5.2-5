package dog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import dto.DogDtoGet
import ru.vsu.cs.tp.paws.R

class DogAdapter (_newDogs: MutableList<DogDtoGet>) : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    private var newDog: MutableList<DogDtoGet> = _newDogs

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val dogItems: View = LayoutInflater.from(parent.context).inflate(R.layout.dog_item, parent, false)
        return DogViewHolder(dogItems)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.dogName.text = newDog[position].name
        holder.dogData.text = newDog[position].birthday
        holder.dogBreed.text = newDog[position].breed


        val bundle = Bundle()

        bundle.putBoolean("sex", newDog[position].sex)
        bundle.putInt("id", newDog[position].id)
        bundle.putString("name", newDog[position].name)
        bundle.putString("date", newDog[position].birthday)
        bundle.putString("breed", newDog[position].breed)

        holder.itemView.setOnClickListener {
            it.findNavController().navigate(R.id.action_profileFragment_to_editDogFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return newDog.size
    }


    class DogViewHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var dogName: TextView = itemView.findViewById(R.id.dogNameTitle)
        var dogData: TextView = itemView.findViewById(R.id.previewDogData)
        var dogBreed: TextView = itemView.findViewById(R.id.dogBreed)

    }
}