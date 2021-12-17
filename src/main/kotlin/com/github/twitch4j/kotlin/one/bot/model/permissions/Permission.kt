package com.github.twitch4j.kotlin.one.bot.model.permissions

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

fun interface Permission {
    fun check(event: ChannelMessageEvent): Boolean
}
