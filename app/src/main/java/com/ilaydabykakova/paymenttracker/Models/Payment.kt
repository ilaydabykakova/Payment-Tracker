package com.ilaydabykakova.paymenttracker.Models

import kotlin.properties.Delegates


class Payment ( var title: String, var amount: Float, var date: String)
{
    var id by Delegates.notNull<Int>() }