package com.ilaydabykakova.paymenttracker.Views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ilaydabykakova.paymenttracker.Database.PaymentOperation
import com.ilaydabykakova.paymenttracker.Models.Payment
import com.ilaydabykakova.paymenttracker.Models.PaymentType
import com.ilaydabykakova.paymenttracker.databinding.ActivityAddPaymentBinding
import java.util.*

class AddPaymentActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddPaymentBinding
    lateinit var dbOperation: PaymentOperation
    lateinit var bringPayment: PaymentType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeEvents()
        setDefaults()

    }

    private fun initializeViews() {
        binding = ActivityAddPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pyDate.maxDate = Calendar.getInstance().timeInMillis
        binding.pyTopicDate.text = "${"Seçilen gün :"} ${getDateText()}"
    }

    private fun initializeEvents() {
        val today = Calendar.getInstance()
        binding.pyDate.init(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, month, day ->
            binding.pyTopicDate.text = "${"Seçilen gün: "} ${getDateText()}"
        }
        binding.btnSavePy.setOnClickListener {
            if (!binding.txtAmount.text.equals("")) {
                dbOperation.addPayment(Payment( bringPayment.title.toString(), binding.txtAmount.text.toString().toFloat(), getDateText().toString()))
                Toast.makeText(this, "Başarılı şekilde ödeminiz eklendi ! "+bringPayment.title, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Lütfen Turar giriniz : ", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("Range")
    private fun setDefaults() {
        dbOperation = PaymentOperation(this)
        val id: Int = intent.getIntExtra("pyTypeId", -1)
        bringPayment = dbOperation.getPaymentTypeId(id)

    }

    private fun getDateText(): String {
        var returnString = ""

        val pyDay: Int = binding.pyDate.dayOfMonth

        val pyMonth: Int = binding.pyDate.month
        val pYear: Int = binding.pyDate.year
        if (pyDay !=null &&pyMonth !=null && pYear !=null) {
            returnString = pyDay.toString() + "." +"0"+(pyMonth+1).toString() + "." + pYear.toString()
        }

        return returnString


        /*
        if (pyDay in 1..9)
            returnString = returnString + "0" + pyDay.toString() + "."
        else
            returnString = "$returnString$pyDay."

        val pyMonth: Int = binding.pyDate.month
        if (pyMonth in 1..9)
            returnString = returnString + pyMonth.toString() + "."
        else
            returnString = returnString + pyDay.toString() + "."

        returnString += binding.pyDate.year

        return returnString
         */
    }


}