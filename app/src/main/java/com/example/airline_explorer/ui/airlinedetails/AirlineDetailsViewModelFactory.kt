package com.example.airline_explorer.ui.airlinedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.airline_explorer.data.source.AirlinesRepository

class AirlineDetailsViewModelFactory(
    private val airlineId: String,
    private val airlinesRepository: AirlinesRepository
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AirlinesDetailsViewModel(airlineId, airlinesRepository) as T
    }
}