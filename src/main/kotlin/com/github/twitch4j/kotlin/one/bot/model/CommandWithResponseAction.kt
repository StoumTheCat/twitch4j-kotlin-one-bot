package com.github.twitch4j.kotlin.one.bot.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.model.permissions.Permission
import com.github.twitch4j.kotlin.one.bot.model.permissions.PermissionLevels.EVERYONE

class CommandWithResponseAction(
    command: String,
    permissions: List<Permission> = listOf(EVERYONE),
    response: (ChannelMessageEvent) -> String
) : CommandAction(command, permissions, response) {

    private val commandWithResponse = fun(event: ChannelMessageEvent) {
        twitchChat.sendMessage(event.channel.name, response.invoke(event))
    }

    override fun getAction(): (ChannelMessageEvent) -> Unit {
        return commandWithResponse
    }
}