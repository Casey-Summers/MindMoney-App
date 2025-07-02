package com.casey.mindmoney.data.DAOs

import androidx.room.*
import com.casey.mindmoney.data.Entities.TransactionEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEnt)

    @Delete
    suspend fun delete(transaction: TransactionEnt)

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<TransactionEnt>>

    @Query("SELECT * FROM transactions WHERE type = :type ORDER BY date DESC")
    fun getTransactionsByType(type: String): Flow<List<TransactionEnt>>

    @Query("SELECT * FROM transactions WHERE type = 'expense' AND expenseType = 'one-off' ORDER BY date DESC")
    fun getOneOffExpenses(): Flow<List<TransactionEnt>>

    @Query("SELECT * FROM transactions WHERE type = 'expense' AND expenseType = 'recurring' ORDER BY date DESC")
    fun getRecurringExpenses(): Flow<List<TransactionEnt>>
}
