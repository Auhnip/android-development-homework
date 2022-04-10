package com.qph.homework

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import homework.R
import homework.databinding.UserInfomationActivityBinding
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class UserInformationActivity : AppCompatActivity() {

	private lateinit var binding: UserInfomationActivityBinding

	private lateinit var timeChangeReceiver: TimeChangeReceiver
	private lateinit var dateTimeFormatter: DateTimeFormatter

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = UserInfomationActivityBinding.inflate(layoutInflater)
		setContentView(binding.root)
//		setContentView(R.layout.user_infomation_activity)

		val intentFilter = IntentFilter()
		intentFilter.addAction("android.intent.action.TIME_TICK")
		timeChangeReceiver = TimeChangeReceiver()
		registerReceiver(timeChangeReceiver, intentFilter)

		// hide title bar
		supportActionBar?.hide()

		// set time display
		dateTimeFormatter = DateTimeFormatter
			.ofPattern(getString(R.string.time_format_text))
//			.withZone(ZoneOffset.ofHours(8))
			.withZone(TimeZone.getDefault().toZoneId())
		binding.timeText.text = dateTimeFormatter.format(Instant.now())

		// set user information
		setUserInformation()

		// set baidu search engine clickable
		binding.searchEngineButton.setOnClickListener {
			val intent = Intent(Intent.ACTION_VIEW)
			intent.data = Uri.parse("https://www.baidu.com")
			startActivity(intent)
		}

		// set back button
		binding.backButton.setOnClickListener {
			backToLoginActivity()
		}
	}

	override fun onBackPressed() {
		backToLoginActivity()
	}

	private fun backToLoginActivity() {
		val insider = binding.insiderEditText.text.toString()
		val returnIntent = Intent()

		// check if insider is empty
		if (insider.isNotEmpty()) {
			returnIntent.putExtra("insider", insider)
			setResult(RESULT_OK, returnIntent)
		} else {
			setResult(RESULT_CANCELED, returnIntent)
		}

		finish()
	}

	private fun setUserInformation() {
		val user: User = intent.getSerializableExtra("userInformation") as User

		binding.userNameText.text = String.format(getString(R.string.user_name_hint),
			user.name.ifEmpty { "佚名" })
		binding.passwordText.text =
			String.format(getString(R.string.password_hint), user.password.ifEmpty { "空" })
	}

	inner class TimeChangeReceiver : BroadcastReceiver() {
		override fun onReceive(p0: Context?, p1: Intent?) {
			// set time display
			binding.timeText.text = dateTimeFormatter.format(Instant.now())

			Toast.makeText(this@UserInformationActivity, "Time changed", Toast.LENGTH_SHORT).show()
		}
	}
}