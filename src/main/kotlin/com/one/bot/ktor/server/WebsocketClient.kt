package com.one.bot.ktor.server


object WebsocketClient {
//    val channel: Channel<String> = Channel(UNLIMITED)
//
//    suspend fun launchWsClient() {
//        val client = HttpClient {
//            install(WebSockets)
//        }
//        runBlocking {
//            client.webSocket(method = HttpMethod.Get, host = "127.0.0.1", port = 8080, path = "/gameinfo") {
//                /*val userInputRoutine = launch { inputMessages() }
//                userInputRoutine.join() // Wait for completion; either "exit" or error*/
//                while (true) {
//                    with(channel.receive()) {
//                        if (this == "exit") return@webSocket
//                        println("WS client received from channel $this")
//                        send(this)
//                    }
//                }
//            }
//        }
//        client.close()
//        println("Connection closed. Goodbye!")
//    }

}