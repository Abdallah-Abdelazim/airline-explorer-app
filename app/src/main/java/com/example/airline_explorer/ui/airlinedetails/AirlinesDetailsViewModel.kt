package com.example.airline_explorer.ui.airlinedetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.airline_explorer.R
import com.example.airline_explorer.data.model.Airline
import com.example.airline_explorer.data.source.AirlinesRepository
import com.example.airline_explorer.util.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class AirlinesDetailsViewModel(
    private val airlineId: String,
    private val airlinesRepository: AirlinesRepository
) : ViewModel() {

    private val _airlineData by lazy {
        MutableLiveData<Airline>().also {
            loadAirlineData()
        }
    }
    val airlineData: LiveData<Airline> get() = _airlineData

    val messageEvent by lazy {
        SingleLiveEvent<Int>()
    }

    val isLoadingEvent by lazy {
        SingleLiveEvent<Boolean>()
    }

    val openWebsiteEvent by lazy {
        SingleLiveEvent<String>()
    }

    private var airlineLoadDisposable: Disposable? = null

    private fun loadAirlineData() {
        Log.d(TAG, "loadAirlinesData")

        isLoadingEvent.value = true

        airlineLoadDisposable = airlinesRepository.getAirline(airlineId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { airline, error ->
                if (error != null || airline == null) {
                    Log.e(TAG, "loadAirlineData error", error)
                    messageEvent.value = R.string.error_network_request
                } else {
                    _airlineData.value = airline
                }

                isLoadingEvent.value = false
            }
    }

    fun openAirlineWebsite() {
        val websiteUrl = airlineData.value?.website
        if (websiteUrl == null) {
            messageEvent.value = R.string.error_missing_airline_website
            return
        }

        openWebsiteEvent.value = websiteUrl
    }

    override fun onCleared() {
        super.onCleared()
        airlineLoadDisposable?.dispose()
    }

    companion object {
        private val TAG = AirlinesDetailsViewModel::class.simpleName
    }
}