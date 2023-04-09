package medical
class ClinicsModel constructor(_id: Int, _title: String, _address: String, _price: String) {

    private var id: Int = _id
    private var title: String = _title
    private var address: String = _address
    private var price: String = _price

    fun getId(): Int {
        return id
    }

    fun setId(_id: Int) {
        id = _id
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
        return title
    }

    fun setPrice(_price: String) {
        title = _price
    }

}