package com.one.bot.features.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

interface ChatAction {
    var enabled: Boolean
    val description: String

    fun dispose()

    fun disable() {
        enabled = false
    }

    fun enable() {
        enabled = true
    }

    fun onChannelMessage(event: ChannelMessageEvent)
    fun getAction(): (ChannelMessageEvent) -> Any
    fun getFilter(event: ChannelMessageEvent): Boolean

    fun checkPermissions(event: ChannelMessageEvent): Boolean
}