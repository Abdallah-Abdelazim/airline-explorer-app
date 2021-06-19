package com.example.airline_explorer.data.source

import android.util.Log
import com.example.airline_explorer.data.model.Airline
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class AirlinesRepository(
    private val airlinesRemoteDataSource: AirlinesDataSource? = null,
    private val airlinesLocalDataSource: AirlinesDataSource? = null,
    private val networkConnectivityChecker: NetworkConnectivityChecker
) : AirlinesDataSource {

    init {
        validateDataSources()
    }

    override fun fetchAirlinesData(): Single<List<Airline>> {
        return chooseSuitableAirlinesDataSource().fetchAirlinesData()
    }

    override fun getAirline(id: String): Single<Airline> {
        return chooseSuitableAirlinesDataSource().getAirline(id)
    }

    override fun addAirline(airline: Airline): Completable {
        return chooseSuitableAirlinesDataSource().addAirline(airline)
    }

    private fun chooseSuitableAirlinesDataSource(): AirlinesDataSource {
        validateDataSources()

        return if (networkConnectivityChecker.isOnline()) {
            Log.d(TAG, "Network status: ONLINE")
            (airlinesRemoteDataSource ?: airlinesLocalDataSource)!!
        } else {
            Log.d(TAG, "Network status: OFFLINE")
            (airlinesLocalDataSource ?: airlinesRemoteDataSource)!!
        }

    }

    private fun validateDataSources() {
        if (airlinesRemoteDataSource == null && airlinesLocalDataSource == null)
            throw IllegalStateException(
                "Both the remote and local data sources can not be null!" +
                        " At least supply one data source."
            )
    }

    companion object {
        private val TAG = AirlinesRepository::class.simpleName
    }
}