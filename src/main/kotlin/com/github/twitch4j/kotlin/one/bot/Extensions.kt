package com.github.twitch4j.kotlin.one.bot

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

val <T> Iterable<T>.twitchList: String
    get() {
        return "⠤".repeat(30).plus(this.map { it.toString() }.reduce { prev, next ->
            "$prev ${"⠤".repeat(30)} $next"
        })
    }

val ChannelMessageEvent.messageWithoutCommand: String
    get() = this.message.substringAfter(" ")

val ChannelMessageEvent.messageArguments: List<String>
    get() = this.messageWithoutCommand.split(Regex("""\s*\|\s*"""))

val ChannelMessageEvent.command: String?
    get() = if (this.message.startsWith("!")) this.message.substringBefore(" ").removePrefix("!")
    else null