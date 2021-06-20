package com.example.airline_explorer

import com.example.airline_explorer.data.model.Airline
import com.example.airline_explorer.data.source.AirlinesDataSource
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class MockedAirlineRemoteDataSource : AirlinesDataSource {

    override fun fetchAirlinesData(): Single<List<Airline>> {
        return Single.just(listOf(AirlinesRepositoryTest.airline))
    }

    override fun getAirline(id: String): Single<Airline> {
        return Single.just(AirlinesRepositoryTest.airline)
    }

    override fun addAirline(airline: Airline): Completable {
        TODO("Not yet implemented")
    }
}