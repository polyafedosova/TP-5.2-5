package dto

import java.util.*

data class DogDtoGet (
    var id: Int,
    var name: String,
    var birthday: Date,
    var sex: Boolean,
    var breed: String
)