package edu.eci.ieti.locaine.ieti.datasource.loginDto

import java.util.*

data class TokenDto (
    val token: String,
    val expirationDate: Date,
)
