package com.casey.mindmoney.data.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEnt(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Tracks transaction's unique ID

    val amount: Double,         // defines the monetary value of the transaction
    val type: String,           // "income", "expense", "investment", "goal", etc
    val category: String,       // "groceries", "Rent"
    val note: String?,          // optional description
    val date: String            // date of transaction (temp string)
)
