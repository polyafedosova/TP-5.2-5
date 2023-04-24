package dog

class DogModel(_id: Int, _name: String, _date: String, _breed: String) {

    private var id: Int = _id
    private var name: String = _name
    private var date: String = _date
    private var breed: String = _breed

    fun getId(): Int {
        return id
    }

    fun setId(_id: Int) {
        this.id = _id
    }

    fun getName(): String {
        return name
    }

    fun setName(_name: String) {
        this.name = _name
    }

    fun getDate(): String {
        return date
    }

    fun setDate(_date: String) {
        this.date = _date
    }

    fun getBreed(): String {
        return breed
    }

    fun setBreed(_breed: String) {
        this.breed = _breed
    }

}