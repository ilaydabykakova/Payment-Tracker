package com.ilaydabykakova.paymenttracker.Views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ilaydabykakova.paymenttracker.databinding.ActivityDetailPaymentBinding
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.ilaydabykakova.paymenttracker.Database.PaymentOperation
import com.ilaydabykakova.paymenttracker.Models.Payment
import com.ilaydabykakova.paymenttracker.Models.PaymentType
import com.ilaydabykakova.paymenttracker.Views.Adapters.PaymentAdapter

class DetailPaymentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPaymentBinding
    private lateinit var dbOperation : PaymentOperation
    private lateinit var bringPaymentType: PaymentType

    var listPayment = ArrayList<Payment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeViews()
        initializeEvents()
        setDefaults()
        binding.txtTypeTitle.setText(bringPaymentType.title)
    }

    private fun initializeViews() {
        binding = ActivityDetailPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvPaymentDetailList.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)

    }

    private fun initializeEvents() {

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, AddPaymentTypeActivity::class.java)
            intent.putExtra("pyTypeId", bringPaymentType.id)
            startActivity(intent)
        }

        binding.btnPaymentAdd.setOnClickListener {
            val intent = Intent(this, AddPaymentActivity::class.java)
            intent.putExtra("pyTypeId", bringPaymentType.id)
            startActivity(intent)
        }
    }

    @SuppressLint("Range")
    private fun setDefaults() {
        dbOperation = PaymentOperation(this)
        val Id = intent.getIntExtra("pyTypeId", -1)
        bringPaymentType = dbOperation.getPaymentTypeId(Id)

        listPayment.clear()
        dbOperation.getAllPayment(bringPaymentType.title, listPayment)

        binding.rvPaymentDetailList.adapter =
            PaymentAdapter(applicationContext, listPayment, ::itemClick)
    }

    private fun itemClick(i: Int) {
        AlertDialog.Builder(this)
            .setTitle("Silme İşlemi")
            .setMessage("Ödemeyi silmek istiyor musunuz ? ")
            .setPositiveButton("Devam") { _, _ ->
                dbOperation.deletePayment(listPayment[i].id)
                Toast.makeText(
                    this,
                   "Ödemeniz başarlı bir şekilde silindi.",
                    Toast.LENGTH_SHORT
                ).show()
                listPayment.removeAt(i)
                binding.rvPaymentDetailList.adapter!!.notifyItemRemoved(i)
            }
            .setNegativeButton("Vazgeç", null).show()
    }
}