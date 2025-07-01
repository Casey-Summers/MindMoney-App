package com.casey.mindmoney.data.DAOs

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.casey.mindmoney.data.Entities.BudgetEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDAO {

    @Query("SELECT * FROM budget_table WHERE id = 1")
    fun getBudget(): Flow<BudgetEnt?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budget: BudgetEnt)

    @Update
    suspend fun update(budget: BudgetEnt)
}
