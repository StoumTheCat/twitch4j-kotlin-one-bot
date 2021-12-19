package com.github.twitch4j.kotlin.one.bot.model

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
    open fun getFilter(event: ChannelMessageEvent): Boolean

    open fun checkPermissions(event: ChannelMessageEvent): Boolean
}