package dto

data class JwtGet (
        val accessToken: String,
        val refreshToken: String,
        )