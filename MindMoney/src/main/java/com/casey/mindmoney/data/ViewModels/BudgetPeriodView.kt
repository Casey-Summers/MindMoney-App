package com.casey.mindmoney.data.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.casey.mindmoney.data.Preferences.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BudgetPeriodView(application: Application) : AndroidViewModel(application) {

    private val prefManager = PreferenceManager(application.applicationContext)

    private val _period = MutableStateFlow("Weekly")
    val period: StateFlow<String> = _period

    init {
        // Load saved value from DataStore
        viewModelScope.launch {
            prefManager.budgetPeriodFlow.collectLatest {
                _period.value = it
            }
        }
    }

    fun setPeriod(newPeriod: String) {
        _period.value = newPeriod

        // Save to DataStore
        viewModelScope.launch {
            prefManager.setBudgetPeriod(newPeriod)
        }
    }
}
