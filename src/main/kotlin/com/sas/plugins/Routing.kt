package com.sas.plugins

import com.sas.routes.customerRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respond(mapOf("Hello" to "World"))
        }
        customerRouting()
    }
}
