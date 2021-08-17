package com.example.Demo1.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.Demo1.model.LoginModel
import com.example.Demo1.roomDB.LoginDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginRepository {
    companion object {

        var loginDatabase: LoginDatabase? = null

        var loginModel: LiveData<LoginModel>? = null

        fun initializeDB(context: Context): LoginDatabase {
            return LoginDatabase.getDataseClient(context)
        }

        fun insertData(context: Context, username: String, password: String) {

            loginDatabase = initializeDB(context)

            CoroutineScope(Dispatchers.IO).launch {
                val loginDetails = LoginModel(username, password)
                loginDatabase!!.loginDao().InsertData(loginDetails)
            }

        }

        fun getLoginDetails(context: Context, username: String) : LiveData<LoginModel>? {

            loginDatabase = initializeDB(context)

            loginModel = loginDatabase!!.loginDao().getLoginDetails(username)

            return loginModel
        }

         fun deleteByUsername(context: Context, username: String) {
            loginDatabase = initializeDB(context)
            loginDatabase!!.loginDao().deleteByUsername(username)

        }
    }

}