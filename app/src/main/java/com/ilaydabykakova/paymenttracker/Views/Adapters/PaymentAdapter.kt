package com.ilaydabykakova.paymenttracker.Views.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilaydabykakova.paymenttracker.Models.Payment
import com.ilaydabykakova.paymenttracker.databinding.RvcPaymentBinding

class PaymentAdapter (val context: Context, var paylist: ArrayList<Payment>, var itemClick: (position: Int) -> Unit
): RecyclerView.Adapter<PaymentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val from = LayoutInflater.from(context)
        val binding = RvcPaymentBinding.inflate(from, parent, false)
        return PaymentViewHolder(binding,itemClick)
    }
    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        holder.bindData(paylist.get(position),context)
    }

    override fun getItemCount(): Int {
        return paylist.size
    }

}