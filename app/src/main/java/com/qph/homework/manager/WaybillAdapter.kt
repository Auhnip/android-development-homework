package com.qph.homework.manager

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.qph.homework.Waybill
import homework.R
import homework.databinding.WaybillEmptyBinding
import homework.databinding.WaybillItemBinding

class WaybillAdapter(private val waybillList: ArrayList<Waybill>) :
    RecyclerView.Adapter<WaybillAdapter.ViewHolder>() {

    private val viewTypeEmpty = 0
    private val viewTypeNormal = 1

    override fun getItemViewType(position: Int): Int =
        if (waybillList.size == 0) viewTypeEmpty else viewTypeNormal

    inner class ViewHolder(
        private val itemBinding: androidx.viewbinding.ViewBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(waybill: Waybill) {
            val binding = itemBinding as WaybillItemBinding
            binding.waybillPositionsText.text =
                context.getString(
                    R.string.waybill_position_text,
                    waybill.transportationDepartureStation,
                    waybill.transportationArrivalStation
                )
            binding.waybillGoodsNameText.text =
                context.getString(
                    R.string.waybill_goods_name_text,
                    waybill.goodsName
                )
            binding.waybillGoodsNumberText.text =
                context.getString(
                    R.string.waybill_goods_number_text,
                    waybill.numberOfPackages
                )
            binding.waybillWaybillNumberText.text =
                context.getString(R.string.waybill_waybill_number_text, waybill.waybillNumber)
            binding.waybillConsigneeText.text = context.getString(
                R.string.waybill_consignee_text,
                waybill.consignee,
                waybill.consigneePhoneNumber
            )
            binding.waybillConsigneePaidText.text = context.getString(
                R.string.waybill_consignee_paid_text,
                waybill.freightPaidByTheReceivingParty
            )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == viewTypeEmpty) {
            val emptyBinding =
                WaybillEmptyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(emptyBinding, parent.context)
        }

        val itemBinding =
            WaybillItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) != viewTypeEmpty) {
            val waybill = waybillList[position]
            holder.bind(waybill)
        }
    }

    override fun getItemCount(): Int = if (waybillList.size > 0) waybillList.size else 1
}