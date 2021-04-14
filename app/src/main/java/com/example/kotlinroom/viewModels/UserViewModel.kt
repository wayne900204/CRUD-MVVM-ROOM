package com.example.kotlinroom.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.kotlinroom.db.UserDao
import com.example.kotlinroom.db.UserDbHelper
import com.example.kotlinroom.db.UserModel
import com.example.kotlinroom.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

//透過 ViewModel 可以將我們的 Repository
// 實體傳入以後，透過 Repository 的方法來取得我們所需的資料
// ，這邊可以看到我們宣告了一個 LiveData 物件，
// 目的是拿來接收從 Repository 得到的資料，最後可以讓 View 來進行存取。
// LiveData 是 Google 開發出來的幫我們處理生命週期時資料存活的工具
// ，也就是說你跟 LiveData 講要跟著誰的(Owner 是哪個 Activity 或 Fragment)生命週期
// ， 那麼它就會隨著該 Owner 活著或消滅。
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG: String = "UserViewModel"
    private val userRepository: UserRepository
    val getAllItems: LiveData<List<UserModel>>

    init {
        val userDao: UserDao = UserDbHelper.getDatabase(application).getRoomDao()
        userRepository = UserRepository(userDao)

        getAllItems = userRepository.getAll()
    }

    fun insert(firstName: String, lastName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val userModel = UserModel(firstName, lastName, getCurrentDate())
            userRepository.insert(userModel)
        }
    }

    fun delete(userModel: UserModel) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.delete(userModel)
        }
    }

     fun update(id: Long,firstName: String,lastName:String) =
        CoroutineScope(Dispatchers.IO).launch {
            val userModel = UserModel(firstName, lastName, getCurrentDate())
            Log.d(TAG, "update: "+userModel)
            userRepository.update(id,firstName,lastName,getCurrentDate())
        }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        return sdf.format(Date())
    }
}
