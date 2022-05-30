package com.qph.homework.view

import android.content.Intent
import android.os.Bundle
import com.qph.homework.manager.ActivityCollector
import com.qph.homework.utils.ConstantsValue
import homework.R
import homework.databinding.ActivityUserInformationBinding

class UserInformationActivity : BaseActivity() {

	private lateinit var binding: ActivityUserInformationBinding

	private lateinit var user: User

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityUserInformationBinding.inflate(layoutInflater)
		setContentView(binding.root)

		user = intent.getSerializableExtra("userInformation") as User

		// set user information
		setUserInformation()

		// set enter waybill button
		binding.enterWaybillButton.setOnClickListener {
			val intent = Intent(this, EnterWaybillActivity::class.java)
			intent.putExtra("departureStation", user.departureStation)
			startActivity(intent)
		}

		// set query local waybill button
		binding.queryLocalWaybillButton.setOnClickListener {
			val intent = Intent(this, WaybillDisplayActivity::class.java)
			intent.putExtra("dataSourceType", ConstantsValue.localSource)
			startActivity(intent)
		}

		// set query company waybill xml button
		binding.queryCompanyWaybillXmlButton.setOnClickListener {
			val intent = Intent(this, WaybillDisplayActivity::class.java)
			intent.putExtra("dataSourceType", ConstantsValue.xmlSource)
			startActivity(intent)
		}

		// set query company waybill json button
		binding.queryCompanyWaybillJsonButton.setOnClickListener {
			val intent = Intent(this, WaybillDisplayActivity::class.java)
			intent.putExtra("dataSourceType", ConstantsValue.jsonSource)
			startActivity(intent)
		}

		// set switch user button
		binding.switchUserButton.setOnClickListener {
			finish()
		}

		// set exit button
		binding.exitButtonUserInfo.setOnClickListener {
			ActivityCollector.finishAll()
		}
	}

	private fun setUserInformation() {
		binding.userNameText.text = String.format(getString(R.string.user_name_hint),
			user.name.ifEmpty { "佚名" })
		binding.passwordText.text =
			String.format(getString(R.string.password_hint), user.password.ifEmpty { "空" })
	}
}