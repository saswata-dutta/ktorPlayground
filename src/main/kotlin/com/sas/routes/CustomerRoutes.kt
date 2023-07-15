package com.sas.routes

import com.sas.database.DdbProvider
import com.sas.models.Customer
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.customerRouting() {
    route("/customer") {

        get("{id?}") {
            val id = call.parameters["id"] ?: return@get call.respond(
                mapOf("ERROR" to "Missing id")
            )
            call.respond(mapOf("DATA" to DdbProvider.getItem(id, Customer::class)))
        }

        post {
            val customer = call.receive<Customer>()
            DdbProvider.putItem(customer, Customer::class)
            call.respond(mapOf("DATA" to customer))
        }

    }
}
