package dto

import java.time.LocalDate
import java.util.*

data class DogDtoGet (
    var id: Int,
    var name: String,
    var birthday: String,
    var sex: Boolean,
    var breed: String
)