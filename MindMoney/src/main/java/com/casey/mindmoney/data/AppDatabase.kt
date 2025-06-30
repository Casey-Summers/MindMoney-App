package com.casey.mindmoney.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.casey.mindmoney.data.DAOs.TransactionDAO
import com.casey.mindmoney.data.Entities.TransactionEnt as TransactionEntity

@Database(
    entities = [TransactionEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transaction(): TransactionDAO
}
