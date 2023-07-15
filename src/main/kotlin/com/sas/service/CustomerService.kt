package com.sas.service

import com.sas.models.Customer

object CustomerService {
    fun get(id: String): Customer {
        val customer = Customer(id = id, firstName = "Sas", lastName = "D", email = "sasd@gmail.com")
        return customer
    }

    fun save(customer: Customer): Unit {
        return
    }
}
