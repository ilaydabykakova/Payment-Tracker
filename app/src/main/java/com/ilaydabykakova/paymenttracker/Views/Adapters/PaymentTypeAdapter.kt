package com.ilaydabykakova.paymenttracker.Views.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ilaydabykakova.paymenttracker.databinding.RvcPaymentTypeBinding
import com.ilaydabykakova.paymenttracker.Models.PaymentType

class PaymentTypeAdapter(val context: Context, var list: ArrayList<PaymentType>, var addPaymentClick: (position: Int) -> Unit, var itemClick: (position: Int) -> Unit) : RecyclerView.Adapter<PaymentTypeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentTypeViewHolder {
        val from = LayoutInflater.from(context)
        val binding = RvcPaymentTypeBinding.inflate(from, parent, false)
        return PaymentTypeViewHolder(context, binding, addPaymentClick, itemClick)
    }
    override fun onBindViewHolder(holder: PaymentTypeViewHolder, position: Int) {
        holder.bindData(list.get(position), context)
    }

    override fun getItemCount(): Int = list.size
}