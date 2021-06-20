package com.example.airline_explorer.ui.viewaddairlines

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

class AirlinesViewModel(private val airlinesRepository: AirlinesRepository) : ViewModel() {

    private val _airlinesData by lazy {
        MutableLiveData<List<Airline>>().also {
            loadAirlinesData()
        }
    }
    val airlinesData: LiveData<List<Airline>> get() = _airlinesData

    private val _isLoadingEvent by lazy {
        MutableLiveData<Boolean>()
    }
    val isLoadingEvent: LiveData<Boolean> get() = _isLoadingEvent

    val messageEvent by lazy {
        SingleLiveEvent<Int>()
    }

    val airlineDetailsNavigationEvent by lazy {
        SingleLiveEvent<Airline>()
    }

    private var airlinesLoadDisposable: Disposable? = null

    private fun loadAirlinesData() {
        Log.d(TAG, "loadAirlinesData")

        _isLoadingEvent.value = true

        airlinesLoadDisposable = airlinesRepository.fetchAirlinesData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { airlinesList, error ->
                if (error != null || airlinesList == null) {
                    Log.e(TAG, "loadAirlinesData error", error)
                    messageEvent.value = R.string.error_network_request
                } else {
                    _airlinesData.value = airlinesList
                }

                _isLoadingEvent.value = false
            }
    }

    fun filter(criteria: String?) {
        Log.d(TAG, "filter: criteria=$criteria")
    }

    fun openAirlineDetails(airline: Airline) {
        if (airline.id == null) {
            messageEvent.value = R.string.error_missing_airline_id
            return
        }

        airlineDetailsNavigationEvent.value = airline
    }

    fun openAddAirlineDialog() {
        Log.d(TAG, "openAddAirlineDialog")
    }

    override fun onCleared() {
        super.onCleared()
        airlinesLoadDisposable?.dispose()
    }

    companion object {
        private val TAG = AirlinesViewModel::class.simpleName
    }
}