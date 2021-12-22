package com.github.twitch4j.kotlin.one.bot

import com.github.twitch4j.chat.TwitchChat
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

val twitchListDelimiter: String = "⠤".repeat(30)
const val twitchMessageLimit = 500

val String.withTwitchDelimiter: String
    get() = this.plus(" $twitchListDelimiter")

val <T> Iterable<T>.twitchList: String
    get() {
        val tempList = twitchListDelimiter.plus(this.map { it.toString() }.reduce { prev, next ->
            "$prev $twitchListDelimiter $next"
        })
        return if (tempList.length < twitchMessageLimit) tempList else "List exceeds 500 characters"
    }

fun <T> TwitchChat.sendAsList(channel: String, items: Collection<T>) {
    var buffer = twitchListDelimiter
    items.forEach {
        if (buffer.length + it.toString().withTwitchDelimiter.length < twitchMessageLimit) {
            buffer += it.toString().withTwitchDelimiter
        } else {
            this.sendMessage(channel, buffer)
            buffer = twitchListDelimiter + it.toString().withTwitchDelimiter
        }
    }
    if (buffer != twitchListDelimiter) {
        this.sendMessage(channel, buffer)
    }
}

fun <T> Collection<T>.diff(other: Collection<T>): Collection<T> {
    return other.filter { it !in this }
}

val ChannelMessageEvent.messageWithoutCommand: String
    get() = this.message.substringAfter(" ")

fun ChannelMessageEvent.getMessageArguments(delimiter: String = """\s*\|\s*"""): List<String> =
    this.messageWithoutCommand.split(Regex(delimiter))

val ChannelMessageEvent.command: String?
    get() = if (this.message.startsWith("!")) this.message.substringBefore(" ").removePrefix("!")
    else null