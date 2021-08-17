package com.example.Demo1.viewModel

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.Demo1.model.LoginModel
import com.example.Demo1.repository.LoginRepository
import java.io.*

import android.provider.OpenableColumns
import android.util.Log
import java.lang.Exception
import android.provider.MediaStore





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

     fun copyFileToInternalStorage(context: Context,drawableId:Int): String? {
        val drawable = ContextCompat.getDrawable(context, drawableId)
        val bitmap = (drawable as BitmapDrawable).bitmap
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String = MediaStore.Images.Media.insertImage(
            context.getContentResolver(),
            bitmap,
            "D_" + System.currentTimeMillis(),
            null
        )
        val uri =  Uri.parse(path)

        val returnCursor: Cursor = context.getContentResolver().query(
            uri, arrayOf(
                OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE
            ), null, null, null
        )!!


        /*
     * Get the column indexes of the data in the Cursor,
     *     * move to the first row in the Cursor, get the data,
     *     * and display it.
     * */
        val nameIndex: Int = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex: Int = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name: String = returnCursor.getString(nameIndex)
        val size = java.lang.Long.toString(returnCursor.getLong(sizeIndex))
        val output: File
        if ("demo" != "") {
            val dir = File(context.getFilesDir().toString() + "/" + "demo")
            if (!dir.exists()) {
                dir.mkdir()
            }
            output = File(context.getFilesDir().toString() + "/" + "demo" + "/" + name)
        } else {
            output = File(context.getFilesDir().toString() + "/" + name)
        }
        try {
            val inputStream: InputStream = context.getContentResolver().openInputStream(uri)!!
            val outputStream = FileOutputStream(output)
            var read = 0
            val bufferSize = 1024
            val buffers = ByteArray(bufferSize)
            while (inputStream.read(buffers).also { read = it } != -1) {
                outputStream.write(buffers, 0, read)
            }
            inputStream.close()
            outputStream.close()
        } catch (e: Exception) {
            e.message?.let { Log.e("Exception", it) }
        }
        return output.path
    }
    }