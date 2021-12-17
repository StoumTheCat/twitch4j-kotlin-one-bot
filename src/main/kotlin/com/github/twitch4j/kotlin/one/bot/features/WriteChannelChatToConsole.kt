package com.github.twitch4j.kotlin.one.bot.features

import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

class WriteChannelChatToConsole(eventHandler: SimpleEventHandler) {
    init {
        eventHandler.onEvent(ChannelMessageEvent::class.java, this::onChannelMessage)
    }

    /** Subscribe to the ChannelMessage Event and write the output to the console */
    private fun onChannelMessage(event: ChannelMessageEvent) {
        System.out.printf(
            "Channel [%s] - User[%s] - Message [%s]%n",
            event.channel.name,
            event.user.name,
            event.message
        )
        event.twitchChat.sendMessage(event.channel.name, """
            Hi there! Your name is ${event.user.name} and you sent us "${event.message}". It was 
            ${event.messageEvent.tags["custom-reward-id"]?.let { "custom reward redeem with id $it" } ?: event.messageEvent.tags["msg-id"]?.let { if (event.messageEvent.tags["msg-id"] == "highlighted-message") "highlighted message, thank you!" else "not a highlighted message, but another default redeem" } ?: "not a custom reward or highlighted message:("}
        """.trimIndent())
    }

}
