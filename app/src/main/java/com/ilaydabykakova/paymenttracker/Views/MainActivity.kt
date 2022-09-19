package com.ilaydabykakova.paymenttracker.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilaydabykakova.paymenttracker.Database.PaymentOperation
import com.ilaydabykakova.paymenttracker.Models.PaymentType
import com.ilaydabykakova.paymenttracker.databinding.ActivityMainBinding

import com.ilaydabykakova.paymenttracker.Views.Adapters.PaymentTypeAdapter

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var dbOperation: PaymentOperation
    var listPaymentTypes = ArrayList<PaymentType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeEvents()
        setDefaults()

    }
    override fun onResume() {
        super.onResume()
        binding.rvPaymentTypeList.adapter!!.notifyDataSetChanged()
    }

    private fun initializeViews() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPaymentTypeList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    private fun initializeEvents() {
        binding.btnNewPaymentType.setOnClickListener {
            val intent = Intent(this, AddPaymentTypeActivity::class.java)
            startActivity(intent)
        }
    }
    private fun setDefaults(){
        listPaymentTypes.clear()

        dbOperation = PaymentOperation(this)
        dbOperation.getPaymentType(listPaymentTypes)

        binding.rvPaymentTypeList.adapter = PaymentTypeAdapter(applicationContext, listPaymentTypes, ::paymentClick,::itemClick)

    }
    private fun paymentClick(i: Int) {
        val intent = Intent(this, AddPaymentActivity::class.java)
        intent.putExtra("pyTypeId", listPaymentTypes[i].id)
        startActivity(intent)
    }
    private fun itemClick(i: Int) {
        val intents = Intent(this, DetailPaymentActivity::class.java)
        intents.putExtra("pyTypeId", listPaymentTypes[i].id)
        startActivity(intents)
    }


}