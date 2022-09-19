package com.ilaydabykakova.paymenttracker.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseOpenHelper(context: Context, name:String, factory: SQLiteDatabase.CursorFactory?, version:Int ) : SQLiteOpenHelper(context,name,factory,version) {
    override fun onCreate(p0: SQLiteDatabase) {
        val sorgu =
            "CREATE TABLE PaymentType(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Title TEXT, Period TEXT, PeriodDay INTEGER)"
        val sorgu2 =
            "CREATE TABLE Payment(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Title TEXT, PaymentAmount REAL, PaymentDate TEXT)"

        p0.execSQL(sorgu)
        p0.execSQL(sorgu2)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}