package com.example.airline_explorer.ui.viewaddairlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.airline_explorer.data.source.AirlinesRepository

class AirlinesViewModelFactory(private val airlinesRepository: AirlinesRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AirlinesViewModel(airlinesRepository) as T
    }
}