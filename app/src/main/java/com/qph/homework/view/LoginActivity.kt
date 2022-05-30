package com.qph.homework.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.qph.homework.manager.ActivityCollector
import com.qph.homework.manager.UserDatabaseHelper
import com.qph.homework.utils.ConstantsValue
import homework.databinding.ActivityLoginBinding
import java.io.Serializable

data class User(val name: String, val password: String, val departureStation: String) : Serializable

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val databaseHelper =
        UserDatabaseHelper(this, ConstantsValue.databaseFileName, ConstantsValue.databaseVersion)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // set login button
        setLoginButton()

        // set exit button
        setExitButton()
    }

    override fun onStop() {
        super.onStop()

        // clear input text
        binding.userNameEditText.text.clear()
        binding.passwordEditText.text.clear()
    }

    override fun onBackPressed() {
        exitApplication()
    }

    @SuppressLint("Range")
    private fun setLoginButton() {
        binding.loginButton.setOnClickListener {

            val database = databaseHelper.readableDatabase

            val userLogin = binding.userNameEditText.text.toString()
            val userPassword = binding.passwordEditText.text.toString()

            // query this user
            val cursor = database.rawQuery(
                "select * from User where user_login=? and user_password=?",
                arrayOf(userLogin, userPassword)
            )

            // if not found
            if (!cursor.moveToFirst()) {
                Toast.makeText(this, "用户名或密码错误", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val departureStation = cursor.getString(cursor.getColumnIndex("departure_station"))
            val userName = cursor.getString(cursor.getColumnIndex("user_name"))
            cursor.close()

            // get input message
            val userInfo = User(userName, userPassword, departureStation)

            // setting intent
            val intent = Intent(this, UserInformationActivity::class.java)
            intent.putExtra("userInformation", userInfo)
            startActivity(intent)
        }
    }

    private fun setExitButton() {
        binding.exitButtonLogin.setOnClickListener {
            // exit app
            exitApplication()
        }
    }

    private fun exitApplication() {
        // exit app
        ActivityCollector.finishAll()
    }

}