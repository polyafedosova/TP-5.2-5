package dto

import java.util.*
import kotlin.properties.Delegates


data class DogDto (
    var name: String,
    var birthday: Date,
    var sex: Boolean,
    var breed: String
)
