package com.casey.mindmoney.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEnt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Unique ID

    val amount: Double,              // Dollar value
    val type: String,                // "income" or "expense"
    val category: String,            // e.g. "rent", "groceries"
    val note: String?,               // Optional user note
    val date: String,                // Stored as ISO or readable format

    val expenseType: String? = null  // "one-off" or "recurring" (invisible to UI)
)
