package com.qph.homework

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import homework.R
import homework.databinding.LoginActivityBinding
import java.io.Serializable

data class User(val name: String, val password: String) : Serializable

class LoginActivity : AppCompatActivity() {

	private lateinit var binding: LoginActivityBinding

	// launcher of next activity
	private val userInformationActivityLauncher =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
			// deal with data from userInformationActivity

			// get name of insider
			val insider = it.data?.getStringExtra("insider")

			// check resultCode
			when (it.resultCode) {
				RESULT_OK -> {
					binding.insiderText.text =
						String.format(getString(R.string.insider_knows_message_hint), insider)
				}
				RESULT_CANCELED -> {
					binding.insiderText.text = String.format(
						getString(R.string.insider_knows_message_hint),
						getString(R.string.insider_default)
					)
				}
			}
		}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = LoginActivityBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)
//		setContentView(R.layout.login_activity)

		// hide title bar
		supportActionBar?.hide()

		// set insider text
		binding.insiderText.text = String.format(
			getString(R.string.insider_knows_message_hint),
			getString(R.string.insider_default)
		)

		// set login button
		setLoginButton()

		// set exit button
		setExitButton()
	}

	override fun onBackPressed() {
		exitApplication()
	}

	private fun setLoginButton() {
		binding.loginButton.setOnClickListener {

			// get input message
			val userInput = User(binding.userNameEditText.text.toString(), binding.passwordEditText.text.toString())

			// setting intent
			val intent = Intent(this, UserInformationActivity::class.java)
			intent.putExtra("userInformation", userInput)

			// launch userInformationActivity
			userInformationActivityLauncher.launch(intent)
		}
	}

	private fun setExitButton() {
		binding.exitButton.setOnClickListener {
			// exit app
			exitApplication()
		}
	}

	private fun exitApplication() {
		// exit app
		Toast.makeText(this, "exited", Toast.LENGTH_SHORT).show()
		finish()
	}

}