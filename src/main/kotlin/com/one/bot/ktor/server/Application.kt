package com.one.bot.ktor.server

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.chat.events.channel.IRCMessageEvent
import com.github.twitch4j.common.enums.CommandPermission
import com.github.twitch4j.common.events.domain.EventChannel
import com.github.twitch4j.common.events.domain.EventUser
import com.one.bot.Twitch
import com.one.bot.resourcesWithoutExtension
import io.ktor.server.websocket.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.websocket.WebSockets
import io.ktor.websocket.*
import java.util.*

object Application {
    private val sessions: MutableMap<String, DefaultWebSocketServerSession> = mutableMapOf()

    suspend fun send(receiver: String, message: String) {
        sessions[receiver]?.send(message)
    }

    fun main() {
        embeddedServer(Netty, port = 8080) {
            install(WebSockets)
            routing {
                static("/") {
                    resources("static")
                    resource("overlay", "overlay.html", "pages")
                    resource("control-panel", "control-panel.html", "pages")
                    resource("table", "table.html", "pages")
                    resource("table-control", "table-control.html", "pages")
                }

                //resourcesWithoutExtension("/", "pages")
            }
            //todo please, kill (rework) me
            routing {
                webSocket("/gameinfo") {
                    send("You are connected!")
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        val receivedText = frame.readText()
                        println("Server received $receivedText")
                        if (receivedText.startsWith("register")) {
                            val split = receivedText.split(" ")
                            sessions[split[1]] = this
                            send("Client registered as ${split[1]}")
                        } else {
                            send("processing '$receivedText'")
                        }
                        if (receivedText.startsWith("!")) {
                            Twitch.twitchClient.eventManager.publish(
                                ChannelMessageEvent(
                                    EventChannel("", "stoumkai"),
                                    object : IRCMessageEvent(
                                        receivedText,
                                        null,
                                        null,
                                        null
                                    ) {
                                        override fun getSubscriberMonths(): OptionalInt {
                                            return OptionalInt.empty()
                                        }

                                        override fun getSubscriptionTier(): OptionalInt {
                                            return OptionalInt.empty()
                                        }
                                    },
                                    EventUser("integration", "ws_integration"),
                                    receivedText,
                                    setOf(
                                        CommandPermission.BROADCASTER,
                                        CommandPermission.EVERYONE,
                                        CommandPermission.MODERATOR
                                    )
                                )
                            )
                        }
                    }
                }
            }

        }.start(wait = true)
    }
}
