package com.casey.mindmoney.data.ViewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.casey.mindmoney.data.DatabaseProvider
import com.casey.mindmoney.data.Entities.TransactionEnt
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = DatabaseProvider.getDatabase(application).transaction()

    val allTransactions: StateFlow<List<TransactionEnt>> = dao.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val incomeTransactions: StateFlow<List<TransactionEnt>> =
        allTransactions.map { list ->
            list.filter { it.type.lowercase() == "income" }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val expenseTransactions: StateFlow<List<TransactionEnt>> =
        allTransactions.map { list ->
            list.filter { it.type.lowercase() == "expense" }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTransaction(transaction: TransactionEnt) {
        viewModelScope.launch {
            dao.insert(transaction)
        }
    }
}
