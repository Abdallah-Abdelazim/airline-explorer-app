package com.example.airline_explorer.data.source.remote

import com.example.airline_explorer.data.model.Airline
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AirlinesService {

    @GET("/v1/airlines")
    fun getAllAirlinesData(): Single<List<Airline>>

    @GET("/v1/airlines/{id}")
    fun getAirlineData(@Path("id") id: String): Single<Airline>

    @POST("/v1/airlines")
    fun addAirlineData(@Body airline: Airline): Single<Airline>
}