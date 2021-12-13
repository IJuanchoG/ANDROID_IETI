package edu.eci.ieti.locaine.ieti.datasource

import edu.eci.ieti.locaine.ieti.datasource.loginDto.LoginDto
import edu.eci.ieti.locaine.ieti.datasource.loginDto.TokenDto
import edu.eci.ieti.locaine.ieti.model.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestLocaineDataSource {

    @POST("auth")
    suspend fun getTokenAuth(@Body loginDto : LoginDto ): TokenDto
}