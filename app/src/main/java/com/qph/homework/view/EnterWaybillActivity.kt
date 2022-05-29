package com.qph.homework.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.qph.homework.manager.UserDatabaseHelper
import com.qph.homework.utils.ConstantsValue
import homework.R
import homework.databinding.ActivityEnterWaybillBinding

class EnterWaybillActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEnterWaybillBinding

    private val databaseHelper: UserDatabaseHelper =
        UserDatabaseHelper(this, ConstantsValue.databaseFileName, ConstantsValue.databaseVersion)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEnterWaybillBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set departure station display
        val departureStation = intent.getStringExtra("departureStation")
        binding.departureStationEditText.text = Editable.Factory.getInstance()
            .newEditable(getString(R.string.departure_station_edit_text, departureStation))
        binding.departureStationEditText.isEnabled = false

        // set save button
        binding.enterWaybillSaveButton.setOnClickListener {
            // check if information is valid
            if (binding.arrivalStationEditText.text.toString().isEmpty()) {
                Toast.makeText(this, "到站信息不可为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.goodsNameEditText.text.toString().isEmpty()) {
                Toast.makeText(this, "货物名称不可为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding.goodsNumberEditText.text.toString().isEmpty()) {
                Toast.makeText(this, "货物件数不可为空", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // save waybill to database
            val database = databaseHelper.writableDatabase
            database.execSQL(
                ConstantsValue.insertWaybillSentence,
                arrayOf(
                    binding.consignorEditText.text.toString().ifEmpty { ConstantsValue.unknownInformationText },
                    binding.consignorPhoneNumberEditText.toString().ifEmpty { ConstantsValue.unknownInformationText },
                    binding.consigneeEditText.text.toString().ifEmpty { ConstantsValue.unknownInformationText },
                    binding.consigneePhoneNumberEditText.text.toString().ifEmpty { ConstantsValue.unknownInformationText },
                    binding.departureStationEditText.text.toString(),
                    binding.arrivalStationEditText.text.toString(),
                    ConstantsValue.unknownInformationText,
                    binding.goodsNameEditText.text.toString(),
                    binding.goodsNumberEditText.text.toString(),
                    binding.consigneePaidEditText.text.toString().ifEmpty { ConstantsValue.unknownInformationInteger },
                    binding.consignorPaidEditText.text.toString().ifEmpty { ConstantsValue.unknownInformationInteger }
                )
            )

            // show hint and return to last activity
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.enterWaybillTurnBackButton.setOnClickListener {
            finish()
        }
    }
}