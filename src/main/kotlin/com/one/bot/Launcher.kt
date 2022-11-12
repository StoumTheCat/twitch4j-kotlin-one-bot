package com.one.bot

import com.one.bot.ktor.server.Application
import com.one.bot.obs.OBSHelper
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


fun main() {
    //OBSHelper
    //PlayerLoader.loadWithStats("")
    runBlocking {
        thread {
            Bot.start()
            Bot.registerFeatures()
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
