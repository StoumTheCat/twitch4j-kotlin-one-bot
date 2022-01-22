package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.features.model.CommandAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel
import com.github.twitch4j.kotlin.one.bot.getMessageArguments

object AddCheckAction : CommandAction(
    command = "addcheck",
    description = "Adds check for either sheriff or don",
    permissions = listOf(PermissionLevel.MODERATOR),
    executable = fun(event: ChannelMessageEvent) {
        val args = event.getMessageArguments("""\s+""")
        val slot = args[0].toInt()
        val checkColor = args[1]
        val checkSlot = args[2]

        TournamentInfo.getPlayerBySlot(slot).checks.add(Pair(checkColor, checkSlot))

        TournamentInfo.sendGameInfo()
    }
) {
}