package com.casey.mindmoney.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_table")
data class BudgetEnt(
    @PrimaryKey val id: Int = 1, // Singleton pattern
    val oneOff: Double = 0.0,
    val recurring: Double = 0.0,
    val goals: Double = 0.0,
    val leftOver: Double = 0.0
)
