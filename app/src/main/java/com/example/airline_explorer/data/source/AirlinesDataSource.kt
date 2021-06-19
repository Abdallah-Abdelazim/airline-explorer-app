package com.example.airline_explorer.data.source

import com.example.airline_explorer.data.model.Airline
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

/**
 * Main entry point for accessing airlines data.
 */
interface AirlinesDataSource {

    fun fetchAirlinesData(): Single<List<Airline>>

    fun getAirline(id: String): Single<Airline>

    fun addAirline(airline: Airline): Completable
}