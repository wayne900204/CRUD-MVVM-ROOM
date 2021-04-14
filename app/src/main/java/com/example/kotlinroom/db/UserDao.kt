package com.example.kotlinroom.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(item: UserModel): Long

    @Insert
    fun insertAll(item: UserModel)

    @Query("SELECT * FROM user_info WHERE id LIKE :id")
    fun findById(id: Long): UserModel

    @Query("SELECT * FROM user_info")
    fun getAll(): LiveData<List<UserModel>>

    @Delete
    fun delete(item: UserModel)

    @Query("UPDATE user_info SET firstName =:firstName, lastName =:lastName, time =:time WHERE id = :id")
    fun update(id: Long, firstName: String, lastName: String, time: String)
}
