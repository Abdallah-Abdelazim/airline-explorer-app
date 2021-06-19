package com.example.airline_explorer.ui.viewaddairlines

import android.util.Log
import androidx.lifecycle.ViewModel

class AirlinesViewModel : ViewModel() {

    fun filter(criteria: String?) {
        Log.d(TAG, "filter: criteria=$criteria")
    }

    fun openAddAirlineDialog() {
        Log.d(TAG, "openAddAirlineDialog")
    }

    companion object {
        private val TAG = AirlinesViewModel::class.simpleName
    }
}