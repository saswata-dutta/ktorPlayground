package com.sas.routes

import com.sas.models.Customer
import com.sas.service.CustomerService
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
            call.respond(mapOf("DATA" to CustomerService.get(id)))
        }

        post {
            val customer = call.receive<Customer>()
            CustomerService.save(customer)
            call.respond(mapOf("DATA" to customer))
        }

    }
}
