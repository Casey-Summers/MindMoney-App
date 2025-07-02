package com.casey.mindmoney.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.casey.mindmoney.data.DAOs.TransactionDAO
import com.casey.mindmoney.data.DAOs.BudgetDAO
import com.casey.mindmoney.data.Entities.TransactionEnt
import com.casey.mindmoney.data.Entities.BudgetEnt

@Database(
    entities = [TransactionEnt::class, BudgetEnt::class],
    version = 3, // increments by 1 with every Room database update
    exportSchema = false // prevents KSP error
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transaction(): TransactionDAO
    abstract fun budget(): BudgetDAO
}
