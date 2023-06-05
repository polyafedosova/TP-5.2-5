package dto

data class OwnerDtoGet (
    val id: Int,
    val username: String,
    val password: String,
    val name: String,
    val roles: Set<String>,
    val show: Boolean
    )

