package com.github.twitch4j.kotlin.one.bot

import com.github.twitch4j.kotlin.one.bot.ktor.server.Application
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


fun main() {
    runBlocking {
        thread {
            Bot.registerFeatures()
            Bot.start()
        }
        thread { Application.main() }
        //thread { launch { WebsocketClient.launchWsClient() } }
        //WebsocketClient.channel.send("Hello from main")
        /*while (true) {
            delay(5000L)
            println("Main thread waiting...")
        }*/
    }
}
