package medical

import androidx.fragment.app.Fragment

class ClinicsModel constructor(_id: Int, _name: String, _services: String, _address: String, _price: String) {

    private var id: Int = _id
    private var services = _services
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

    fun getService(): String {
        return services
    }

    fun setService(_service: String) {
        services = _service
    }

    fun getAddress(): String {
        return address
    }

    fun setAddress(_address: String) {
        address = _address
    }

    fun getPrice(): String {
        return price
    }

    fun setPrice(_price: String) {
        price = _price
    }

}