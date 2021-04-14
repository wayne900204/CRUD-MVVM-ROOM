package com.example.kotlinroom.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(UserModel::class)], version = 1, exportSchema = false)
abstract class UserDbHelper : RoomDatabase() {
    abstract fun getRoomDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDbHelper? = null
        fun getDatabase(context: Context): UserDbHelper {
            // if(Null) then create database
            if (INSTANCE == null) {
                INSTANCE =
                    Room.databaseBuilder(
                        context,
                        UserDbHelper::class.java,
                        "USER"
                    ).build()
            }
            return INSTANCE as UserDbHelper
        }
    }
}
