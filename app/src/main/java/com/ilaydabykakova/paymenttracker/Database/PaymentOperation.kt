package com.ilaydabykakova.paymenttracker.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.ilaydabykakova.paymenttracker.Models.Payment
import com.ilaydabykakova.paymenttracker.Models.PaymentType

class PaymentOperation(context : Context) {
    var db : SQLiteDatabase? = null
    var dbOpenHelper : DatabaseOpenHelper

    init {
        //FACTORY verileri parça parça okuycak
        dbOpenHelper = DatabaseOpenHelper(context,"PaymentDB",null,1)
    }
    fun open(){
        db = dbOpenHelper.writableDatabase
    }
    fun close(){
        if (db !=null && db!!.isOpen){
            db!!.close()
        }
    }
    //-- Payment Type --
    //Ödeme Tipi EKleme
    fun addPaymentType(py: PaymentType) : Long?
    {
        val cv = ContentValues()
        cv.put("Title", py.title)
        cv.put("Period", py.period)
        cv.put("PeriodDay", py.periodDay)
        var kayit: Long?=null
        open()
        try {
            kayit = db!!.insert("PaymentType", null, cv)
        } catch (error: SQLiteException) {
            Log.e("Exception", "SQLException" + error.localizedMessage)
        }
        close()

        return kayit
    }
    //Ödeme tipi Güncelle
    fun updatePaymentType(inputId: Int, py: PaymentType): Int {
        val cv = ContentValues()
        cv.put("Title", py.title)
        cv.put("Period", py.period)
        cv.put("PeriodDay", py.periodDay)
        open()
        val output = db!!.update("PaymentType", cv, "Id = ?", arrayOf(inputId.toString()))
        close()
        return output
    }
    fun deletePaymentType(id: Int) {
        open()
        db!!.delete("PaymentType", "Id = ?", arrayOf(id.toString()))
        close()
    }
    private fun getAllPaymentType():Cursor
    {
        var sorgu = "Select * from  PaymentType"
        return db!!.rawQuery(sorgu,null)
    }
    @SuppressLint("Range")
    fun getPaymentType(typeList : ArrayList<PaymentType>)
    {
        open()
        val c : Cursor = getAllPaymentType()
        if (c.moveToFirst()) {
            do {
                typeList.add(
                    PaymentType(
                        c.getString(c.getColumnIndex("Title")),
                        c.getString(c.getColumnIndex("Period")),
                        c.getInt(3)
                    ).apply {
                        id = c.getInt(0)
                })
            } while (c.moveToNext())
        }
        close()
    }
    @SuppressLint("Range")
    fun getPaymentTypeId(inputId: Int): PaymentType {
        open()
        val query = "SELECT * FROM PaymentType WHERE Id = ?"
        val c: Cursor = db!!.rawQuery(query, arrayOf(inputId.toString()))
        c.moveToFirst()
        val getType :PaymentType = PaymentType(
            c.getString(c.getColumnIndex("Title")),
            c.getString(c.getColumnIndex("Period")),
            c.getInt(3)
        ).apply {
            id = c.getInt(0)
        }
        c.close()
        close()
        return getType
    }

    // ---- PAYMENT ----

    fun addPayment(py: Payment) : Long?
    {
        val cv = ContentValues()
        cv.put("Title", py.title)
        cv.put("PaymentAmount", py.amount)
        cv.put("PaymentDate", py.date)
        open()
        var output: Long = -1
        try {
            output = db!!.insert("Payment", null, cv)
        } catch (error: SQLiteException) {
            Log.e("Exception", "SQLException" + error.localizedMessage)
        }
        close()
        return output
    }

    fun deletePayment(id: Int) {
        open()
        db!!.delete("Payment", "Id = ?", arrayOf(id.toString()))
        close()
    }

    private fun getPayment():Cursor
    {
        var sorgu = "Select * from  Payment"
        return db!!.rawQuery(sorgu,null)
    }
    fun rawQueryGetPaymentTitle(title: String): Cursor {
        val query = "SELECT * FROM Payment  WHERE Title = ?"
        return db!!.rawQuery(query, arrayOf(title))
    }

    @SuppressLint("Range")
    fun getAllPayment(title: String,payList : ArrayList<Payment>)
    {
        open()
        val c: Cursor = rawQueryGetPaymentTitle(title)
        if (c.moveToFirst()) {
            do {
                payList.add(
                    Payment(
                        c.getString(c.getColumnIndex("Title")),
                        c.getFloat(2),
                        c.getString(c.getColumnIndex("PaymentDate"))
                    ).apply {
                        id = c.getInt(0)
                    }
                )
            } while (c.moveToNext())
        }
        close()
    }
    @SuppressLint("Range")
    fun getPaymentId(inputId: Int): Payment {
        open()
        val query = "SELECT * FROM Payment WHERE Id = ?"
        val c: Cursor = db!!.rawQuery(query, arrayOf(inputId.toString()))
        c.moveToFirst()
        val getPay = Payment(
            c.getString(c.getColumnIndex("Title")),
            c.getFloat(2),
            c.getString(c.getColumnIndex("PaymentDate"))
        ).apply {
            id = c.getInt(0)
        }
        c.close()
        close()
        return getPay
    }



}