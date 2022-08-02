package com.one.bot.features.model.permissions

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

fun interface Permission {
    fun check(event: ChannelMessageEvent): Boolean
}
