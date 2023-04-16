package event

import java.util.Date

class EventsModel constructor(_name: String, _date: Date, _comment: String) {

    private var name: String = _name
    private var date: Date = _date
    private var comment: String = _comment

    fun getName(): String {
        return name
    }

    fun setName(_name: String) {
        this.name = _name
    }

    fun getDate(): Date {
        return date
    }

    fun setDate(_date: Date) {
        this.date = _date
    }

    fun getComment(): String {
        return comment
    }

    fun setComment(_comment: String) {
        this.comment = _comment
    }

}