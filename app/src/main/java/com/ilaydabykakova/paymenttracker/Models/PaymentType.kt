package com.ilaydabykakova.paymenttracker.Models

import java.io.Serializable
import kotlin.properties.Delegates


class PaymentType(var title: String) : Serializable {
    var id by Delegates.notNull<Int>()
    var period: String? = null
    var periodDay: Int = 0


    constructor(title: String, period: String, paymentDay: Int) : this(title) {
        this.period = period
        this.periodDay = paymentDay
    }

}