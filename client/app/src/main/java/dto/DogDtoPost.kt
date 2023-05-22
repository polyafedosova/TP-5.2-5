package dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDate
import java.util.*


data class DogDtoPost (
    var name: String,
    var birthday: String,
    var sex: Boolean,
    var breed: String
)
