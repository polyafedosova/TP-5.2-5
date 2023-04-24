package admin

class AdminClinicsModel constructor(_name: String, _address: String, _price: String, _services: String,
                                    _phone: String) {

    private var name = _name
    private var address: String = _address
    private var price: String = _price
    private var services: String = _services
    private var phone: String = _phone


    fun getName(): String {
        return name
    }

    fun setName(_name: String) {
        name = _name
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

    fun getServices(): String {
        return services
    }

    fun setServices(_services: String) {
        services = _services
    }

    fun getPhone(): String {
        return phone
    }

    fun setPhone(_phone: String) {
        phone = _phone
    }
}