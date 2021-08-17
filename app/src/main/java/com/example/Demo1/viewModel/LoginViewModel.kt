package com.example.Demo1.viewModel

import android.R.attr
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.Demo1.R
import com.example.Demo1.model.LoginModel
import com.example.Demo1.repository.LoginRepository
import java.io.*
import android.provider.MediaStore

import android.content.ContentValues

import android.content.ContentResolver

import android.os.Build
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.OnScanCompletedListener
import java.lang.Exception
import android.R.attr.data





class LoginViewModel : ViewModel() {

    var liveDataLogin: LiveData<LoginModel>? = null

    fun insertData(context: Context, username: String, password: String) {
        LoginRepository.insertData(context, username, password)
    }

    fun getLoginDetails(context: Context, username: String) : LiveData<LoginModel>? {
        liveDataLogin = LoginRepository.getLoginDetails(context, username)
        return liveDataLogin
    }

    fun deleteUser(context: Context, username: String) {
        LoginRepository.deleteByUsername(context, username)
    }
    fun saveImageToInternalStorage(context: Context,drawableId:Int) {
           val drawable = ContextCompat.getDrawable(context, drawableId)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val createFolder = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "test"
        )
        if (!createFolder.exists()) createFolder.mkdir()
        val saveImage = File(createFolder, "D_" + System.currentTimeMillis() + ".png")
        try {
            val outputStream: OutputStream = FileOutputStream(saveImage)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    }