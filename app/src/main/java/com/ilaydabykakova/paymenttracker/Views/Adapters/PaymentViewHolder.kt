package com.ilaydabykakova.paymenttracker.Views.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.ilaydabykakova.paymenttracker.Models.Payment
import com.ilaydabykakova.paymenttracker.R
import com.ilaydabykakova.paymenttracker.databinding.RvcPaymentBinding

class PaymentViewHolder (private val binding: RvcPaymentBinding, var itemClick: (position: Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.clPayment.setOnClickListener {
            itemClick(adapterPosition)
        }
    }

    fun bindData(p:Payment,context: Context) {
        binding.txtPayment.text = p.amount.toString() + "TL" + ""+ ","
        binding.txtDate.text = " ödendi. - "+ " " + p.date.toString()

    }
}