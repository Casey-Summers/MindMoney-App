package com.casey.mindmoney.data

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            Room.databaseBuilder(
                context.applicationContext,   // prevents memory leaks from Activity
                AppDatabase::class.java,      // tells Room which DB class to build
                "mindmoney_database"    // file name for the actual SQLite DB
            ).build().also { INSTANCE = it }  // stores and returns it
        }
    }
}
