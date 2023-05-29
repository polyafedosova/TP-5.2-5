package dto

import java.util.*

data class EventDtoGet (
    var id: Int,
    var name: String,
    var date: String,
    var time: String,
    var description: String
)