package com.example.Demo1.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.Demo1.R
import com.example.Demo1.databinding.ActivityLoginBinding
import com.example.Demo1.viewModel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var loginViewModel: LoginViewModel

    lateinit var context: Context
    private lateinit var binding: ActivityLoginBinding
    lateinit var strUsername: String
    lateinit var strPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this@LoginActivity
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.btnLogin.setOnClickListener {

            strUsername = binding.txtUsername.text.toString().trim()
            strPassword = binding.txtPassword.text.toString().trim()

            if (strUsername.isEmpty()) {
                binding.txtUsername.error = "Enter Username"
            } else if (strPassword.isEmpty()) {
                binding.txtPassword.error = "Enter Password"
            } else {
                loginViewModel.insertData(context, strUsername, strPassword)
                val intent = Intent(this, LoginSuccessActivity::class.java)
                intent.putExtra("username", strUsername);
                startActivity(intent)

            }
        }
    }

}