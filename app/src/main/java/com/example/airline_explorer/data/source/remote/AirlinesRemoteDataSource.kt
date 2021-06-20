package com.example.airline_explorer.data.source.remote

import com.example.airline_explorer.data.model.Airline
import com.example.airline_explorer.data.source.AirlinesDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class AirlinesRemoteDataSource(private val airlinesService: AirlinesService) : AirlinesDataSource {

    override fun fetchAirlinesData(): Single<List<Airline>> {
        return airlinesService.getAllAirlinesData()
    }

    override fun getAirline(id: String): Single<Airline> {
        return airlinesService.getAirlineData(id)
    }

    override fun addAirline(airline: Airline): Completable {
        TODO("Not yet implemented")
    }
}