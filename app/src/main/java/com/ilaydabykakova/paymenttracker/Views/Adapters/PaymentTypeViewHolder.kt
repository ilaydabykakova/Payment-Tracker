package com.ilaydabykakova.paymenttracker.Views.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.ilaydabykakova.paymenttracker.Models.Payment
import com.ilaydabykakova.paymenttracker.Models.PaymentType
import com.ilaydabykakova.paymenttracker.databinding.RvcPaymentBinding
import com.ilaydabykakova.paymenttracker.databinding.RvcPaymentTypeBinding

class PaymentTypeViewHolder(private val context: Context, private val binding: RvcPaymentTypeBinding, var addPaymentClick: (position: Int) -> Unit, var itemClick: (position: Int) -> Unit): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.btnAddPaymentType.setOnClickListener {
            addPaymentClick(adapterPosition)
        }

        binding.clPaymentType.setOnClickListener {
            itemClick(adapterPosition)
        }
    }
    fun bindData(paymentType: PaymentType, context: Context) {
        binding.txtTypes.setText(paymentType.title)
        if (paymentType.period != "Gününüz seçemediniz...") {
            when (paymentType.period) {
                "YILLIK" -> binding.txtTypePeriod.text = "Yıllık Ödeme Planı"
                "AYLIK" -> binding.txtTypePeriod.text = "Aylık Ödeme Planı"
                "HAFTALIK" -> binding.txtTypePeriod.text = "Haftalık Ödeme Planı"
                else -> {
                    binding.txtTypes.text = "ERR0R"
                }
            }
        }
        binding.txtTypePeriodDay.text = "İlk eklenen "+paymentType.periodDay.toInt().toString() + ". gün."
    }
}