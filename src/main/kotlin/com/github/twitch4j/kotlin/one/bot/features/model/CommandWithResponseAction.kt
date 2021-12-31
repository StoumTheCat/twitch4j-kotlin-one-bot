package com.github.twitch4j.kotlin.one.bot.features.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.Twitch.twitchChat
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.Permission
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel.EVERYONE
import com.github.twitch4j.kotlin.one.bot.sendAsList

open class CommandWithResponseAction(
    command: String,
    permissions: List<Permission> = listOf(EVERYONE),
    description: String = "Default command with response action",
    response: (ChannelMessageEvent) -> List<String>
) : CommandAction(command, permissions, description, response) {

    private val commandWithResponse = fun(event: ChannelMessageEvent) {
        val responseLines = response.invoke(event)
        if (responseLines.size == 1) {
            twitchChat.sendMessage(event.channel.name, responseLines[0])
        } else {
            twitchChat.sendAsList(event.channel.name, responseLines)
        }
    }

    override fun getAction(): (ChannelMessageEvent) -> Unit {
        return commandWithResponse
    }
}