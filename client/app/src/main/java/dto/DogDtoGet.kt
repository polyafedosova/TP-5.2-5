package dto


data class DogDtoGet (
    var id: Int,
    var name: String,
    var birthday: String,
    var sex: Boolean,
    var breed: String
)