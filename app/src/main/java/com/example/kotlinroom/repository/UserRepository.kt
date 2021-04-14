package com.example.kotlinroom.repository

import com.example.kotlinroom.db.UserDao
import com.example.kotlinroom.db.UserModel

class UserRepository(private val dao: UserDao) {

    fun insert(userModel: UserModel): Long = dao.insert(userModel)

    fun update(id: Long,firstName: String,lastName:String,time:String) = dao.update(id,firstName,lastName,time)

    fun delete(userModel: UserModel) = dao.delete(userModel)

    fun select(id: Long): UserModel = dao.findById(id)

    fun getAll() = dao.getAll()
}
