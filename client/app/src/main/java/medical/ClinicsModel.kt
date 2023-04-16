package medical

import androidx.fragment.app.Fragment

class ClinicsModel constructor(_id: Int, _name: String,_title: String, _address: String, _price: String) {

    private var id: Int = _id
    private var title: String = _title
    private var name = _name
    private var address: String = _address
    private var price: String = _price

    fun getId(): Int {
        return id
    }

    fun setId(_id: Int) {
        id = _id
    }

    fun getName(): String {
        return name
    }

    fun setName(_name: String) {
        name = _name
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(_title: String) {
        title = _title
    }

    fun getAddress(): String {
        return address
    }

    fun setAddress(_address: String) {
        title = _address
    }

    fun getPrice(): String {
        return price
    }

    fun setPrice(_price: String) {
        price = _price
    }

}