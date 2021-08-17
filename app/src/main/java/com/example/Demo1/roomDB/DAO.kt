package com.example.Demo1.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.Demo1.model.LoginModel

@Dao
interface DAO {
    @Insert()
    suspend fun InsertData(loginTableModel: LoginModel)

    @Query("SELECT * FROM Login WHERE Username =:username")
    fun getLoginDetails(username: String?) : LiveData<LoginModel>

    @Query("DELETE FROM Login WHERE username = :username")
    fun deleteByUsername(username: String?)
}