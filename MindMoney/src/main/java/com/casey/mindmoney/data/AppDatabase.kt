package com.casey.mindmoney.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.casey.mindmoney.data.DAOs.TransactionDAO
import com.casey.mindmoney.data.DAOs.BudgetDAO
import com.casey.mindmoney.data.Entities.TransactionEnt
import com.casey.mindmoney.data.Entities.BudgetEnt

@Database(
    entities = [TransactionEnt::class, BudgetEnt::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transaction(): TransactionDAO
    abstract fun budget(): BudgetDAO
}
