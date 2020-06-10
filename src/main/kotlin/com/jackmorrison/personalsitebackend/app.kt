package com.jackmorrison.personalsitebackend

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty


fun main(args: Array<String>) {


    val port = System.getenv("PORT")?.toInt() ?: 23567
    embeddedServer(Netty,port) {
        install(ContentNegotiation) {
            gson {
                setPrettyPrinting()
            }
        }
        routing {
            get("") {
                call.respond("I'm alive!")

                val dao = FragmentDAO()
//                val saveResult = dao.save("{\"type\":\"song\",\"imgUrl\":\"https://jackmorrison.xyz\",\"time\":\"2020-01-01\",\"body\":\"Testing\"}")
//                println(saveResult)
                val result = dao.getAll()
                println(result)

            }
            get("hello") {
                call.respond(HttpStatusCode.Accepted, "Hello")
            }
            get("random/{min}/{max}") {
                val min = call.parameters["min"]?.toIntOrNull() ?: 0
                val max = call.parameters["max"]?.toIntOrNull() ?: 10
                val randomString = "${(min until max).shuffled().last()}"
                call.respond(mapOf("value" to randomString))
            }
        }
    }.start(wait = true)

}