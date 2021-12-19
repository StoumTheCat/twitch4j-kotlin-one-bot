package com.github.twitch4j.kotlin.one.bot.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.model.permissions.Permission
import com.github.twitch4j.kotlin.one.bot.model.permissions.PermissionLevels.EVERYONE

open class CommandWithResponseAction(
    command: String,
    permissions: List<Permission> = listOf(EVERYONE),
    description: String = "Default command with response action",
    response: (ChannelMessageEvent) -> String
) : CommandAction(command, permissions, description, response) {

    private val commandWithResponse = fun(event: ChannelMessageEvent) {
        twitchChat.sendMessage(event.channel.name, response.invoke(event))
    }

    override fun getAction(): (ChannelMessageEvent) -> Unit {
        return commandWithResponse
    }
}