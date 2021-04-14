package com.example.kotlinroom.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserModel(

    @ColumnInfo(name = "firstName")
    var firstName: String,
    @ColumnInfo(name = "lastName")
    var lastName: String,
    @ColumnInfo(name = "time")
    val createdAt: String,
//    @ColumnInfo(name = "modified_at")
//    val modifiedAt: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
