package com.casey.mindmoney.data.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.casey.mindmoney.data.DatabaseProvider
import com.casey.mindmoney.data.Entities.BudgetEnt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BudgetView(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider.getDatabase(application).budget()

    private val _budget = MutableStateFlow(BudgetEnt())
    val budget: StateFlow<BudgetEnt> = _budget

    init {
        viewModelScope.launch {
            dao.getBudget().collectLatest { stored ->
                if (stored != null) {
                    _budget.value = stored
                } else {
                    dao.insert(BudgetEnt())
                }
            }
        }
    }

    fun updateField(field: String, value: Double) {
        val current = _budget.value
        val updated = when (field) {
            "oneOff" -> current.copy(oneOff = value)
            "recurring" -> current.copy(recurring = value)
            "goals" -> current.copy(goals = value)
            "leftOver" -> current.copy(leftOver = value)
            else -> current
        }
        _budget.value = updated
        viewModelScope.launch {
            dao.insert(updated)
        }
    }
}
