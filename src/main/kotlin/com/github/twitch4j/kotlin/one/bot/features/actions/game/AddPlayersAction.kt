package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevels
import com.github.twitch4j.kotlin.one.bot.messageArguments

object AddPlayersAction : CommandWithResponseAction(
    "addplayers",
    description = "Add players with | delimiter",
    permissions = listOf(PermissionLevels.MODERATOR),
    response = fun(event: ChannelMessageEvent): String {
        val players = event.messageArguments
        return ""
    }
) {
    init {
        aliases.addAll(listOf("addp", "players"))
    }
}