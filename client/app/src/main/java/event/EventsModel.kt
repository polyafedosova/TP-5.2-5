package event

import java.util.Date

class EventsModel(_id: Int, _name: String, _date: String, _comment: String) {

    private var id: Int = _id
    private var name: String = _name
    private var date: String = _date
    private var comment: String = _comment

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

    fun getComment(): String {
        return comment
    }

    fun setComment(_comment: String) {
        this.comment = _comment
    }

}