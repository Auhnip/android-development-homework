package com.qph.homework.view

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.qph.homework.Waybill
import com.qph.homework.manager.UserDatabaseHelper
import com.qph.homework.manager.WaybillAdapter
import com.qph.homework.utils.ConstantsValue
import com.qph.homework.utils.Utilities
import homework.R
import homework.databinding.ActivityWaybillDisplayBinding

class WaybillDisplayActivity : BaseActivity() {

    lateinit var binding: ActivityWaybillDisplayBinding

    private var type = ConstantsValue.localSource

    private val databaseHelper = UserDatabaseHelper(this, ConstantsValue.databaseFileName, ConstantsValue.databaseVersion)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaybillDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.waybillList.layoutManager = layoutManager

        type = intent.getIntExtra("dataSourceType", ConstantsValue.localSource)
        when (type) {
            ConstantsValue.localSource -> {
                Utilities.updateWaybillFromLocal(databaseHelper, this)
                binding.waybillDisplayTitle.text = getString(R.string.local_waybill_title)
            }
            ConstantsValue.xmlSource -> {
                Utilities.updateWaybillFromHttpXml(ConstantsValue.xmlDataUrl, this)
                binding.waybillDisplayTitle.text = getString(R.string.company_waybill_xml_title)
            }
            ConstantsValue.jsonSource -> {
                Utilities.updateWaybillFromHttpJson(ConstantsValue.jsonDataUrl, this)
                binding.waybillDisplayTitle.text = getString(R.string.company_waybill_json_title)
            }
        }

        binding.turnBackButton.setOnClickListener {
            finish()
        }
    }

    fun updateWaybillList(list: ArrayList<Waybill>) {
        runOnUiThread {
            val adapter = WaybillAdapter(list)
            binding.waybillList.adapter = adapter
        }
    }
}