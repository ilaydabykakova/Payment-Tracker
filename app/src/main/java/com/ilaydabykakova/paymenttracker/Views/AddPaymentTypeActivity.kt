package com.ilaydabykakova.paymenttracker.Views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ilaydabykakova.paymenttracker.Database.PaymentOperation
import com.ilaydabykakova.paymenttracker.databinding.ActivityAddPaymentTypeBinding
import com.ilaydabykakova.paymenttracker.Models.PaymentType

class AddPaymentTypeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddPaymentTypeBinding
    private lateinit var dbOperation: PaymentOperation

    private var doesExists: Boolean = false
    lateinit var bringPy: PaymentType


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeViews()
        initializeEvents()
        setDefaults()

    }

    private fun initializeViews() {
        binding = ActivityAddPaymentTypeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listPeriod = arrayListOf("Seçim Yapınız...","YILLIK","AYLIK","HAFTALIK")
        val adp: ArrayAdapter<String> = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listPeriod)
        binding.spnPeriod.adapter = adp
    }

    private fun initializeEvents() {
        binding.btnSaveType.setOnClickListener {
            if (doesExists) {
                updatePaymentType()
            } else {
                createPaymentType()
            }
        }
        binding.btnRemoveType.setOnClickListener {
            if (doesExists) {
                AlertDialog.Builder(this)
                    .setTitle("Ödeme tipini silmek istiyor musunuz ?")
                    .setMessage("Bu ödeme tipi db'den silinicektir. ")
                    .setPositiveButton(
                        "Devam"
                    ) { _, _ ->
                        dbOperation.deletePaymentType(bringPy.id)
                        Toast.makeText(
                            this,
                            "Ödem tipi başarılı bir şekilde silindi.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val goPaymentTypeList= Intent(this, MainActivity::class.java)
                        startActivity(goPaymentTypeList)
                    }
                    .setNegativeButton("Vazgeç", null)
                    .show()
            }
        }

    }
    @SuppressLint("Range")
    private fun setDefaults() {
        dbOperation = PaymentOperation(this)
        val id: Int = intent.getIntExtra("pyTypeId", -1)

        doesExists = id != -1
        if (doesExists) {
            //id -1 değil ve payment type click edilen id al.
            bringPy = dbOperation.getPaymentTypeId(id)
            //Verileri viewslara set et.
            updateViews()
        } else {
            binding.btnRemoveType.visibility = View.INVISIBLE
        }
    }
    //Kayedilen verileri set edilmeli ve paymenttype class çekildi.
    private fun updateViews() {
        binding.btnRemoveType.visibility = View.VISIBLE
        binding.btnSaveType.visibility = View.VISIBLE
        binding.txtTypeTitle.setText(bringPy.title)
        when (bringPy.period) {
            "YILLIK" -> binding.spnPeriod.setSelection(1)
             "AYLIK" -> binding.spnPeriod.setSelection(2)
            "HAFTALIK" -> binding.spnPeriod.setSelection(3)
            else -> {
                binding.spnPeriod.setSelection(0)
            }
        }
        binding.txtPeriodDay.setText(bringPy.periodDay.toString())
    }
        private fun periodControl(period: String, peroidDay: Int): Boolean {
            if (period == "YILLIK"){
                return peroidDay in 1..365
            }else if (period == "AYLIK"){
                return peroidDay in 1..31
            }else if (period == "HAFTALIK"){
                return  peroidDay in 1..7
            }

            return false
        }

    //Gelen verileri güncelle.
    private fun updatePaymentType() {
        val pyTitle: String = binding.txtTypeTitle.text.toString()
        val pyPeriod: String = binding.spnPeriod.selectedItem.toString()
        val pyPeriodDay: Int = binding.txtPeriodDay.text.toString().toInt()
        binding.btnRemoveType.visibility = View.VISIBLE
        binding.btnSaveType.visibility = View.VISIBLE

        if(pyTitle !=""){
            if(pyPeriod == "Seçim Yapınız..."){
                dbOperation.updatePaymentType(bringPy.id, PaymentType(pyTitle,"Gününüzü Seçiniz...",0))
                Toast.makeText(this, "Başarılı, Bilgileriniz tekrar güncelletebilirsiniz.", Toast.LENGTH_SHORT).show()
                val pyListIntent = Intent(this, MainActivity::class.java)
                startActivity(pyListIntent)
            }else{
                if(periodControl(pyPeriod,pyPeriodDay)){
                    dbOperation.updatePaymentType(bringPy.id, PaymentType(pyTitle,pyPeriod,pyPeriodDay))
                    Toast.makeText(this, "Başarılı şekilde yeni ödeme tipi güncelelndi.", Toast.LENGTH_LONG).show()
                    val pyListIntents = Intent(this, MainActivity::class.java)
                    startActivity(pyListIntents)
                }else {
                    Toast.makeText(
                        this, "Yanlış veri giridiniz !", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(this, "Bilgilerinizi boş girdiniz !", Toast.LENGTH_SHORT).show()
        }

    }

    private fun createPaymentType() {
        val pyTitle: String = binding.txtTypeTitle.text.toString()
        val pyPeriod: String = binding.spnPeriod.selectedItem.toString()
        val pyPeriodDay: Int = binding.txtPeriodDay.text.toString().toInt()
        binding.btnRemoveType.visibility = View.INVISIBLE
        binding.btnSaveType.visibility = View.VISIBLE
        if (pyTitle != "") {
            if (pyPeriod == "Seçim Yapınız...") {
                dbOperation.addPaymentType(PaymentType(pyTitle, pyPeriod, 0))
                Toast.makeText(this, "Başarılı, Bilgileriniz tekrar güncelletebilirsiniz.", Toast.LENGTH_SHORT)
                val pyListIntent = Intent(this, MainActivity::class.java)
                startActivity(pyListIntent)
            } else {
                if (periodControl(pyPeriod, pyPeriodDay)) {
                    dbOperation.addPaymentType(PaymentType(pyTitle, pyPeriod, pyPeriodDay))
                    Toast.makeText(this, "Başarılı şekilde yeni ödeme tipi oluşturuldu.", Toast.LENGTH_SHORT)
                    val pyListIntent = Intent(this, MainActivity::class.java)
                    startActivity(pyListIntent)
                } else {
                    Toast.makeText(this, "Yanlış veri giridiniz !", Toast.LENGTH_SHORT).show()
                }
            }
        }else {
            Toast.makeText(this, "Bilgilerinizi boş girdiniz !", Toast.LENGTH_SHORT).show()

        }
    }
}