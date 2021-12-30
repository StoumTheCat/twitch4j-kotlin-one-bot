package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.Twitch.twitchChat
import com.github.twitch4j.kotlin.one.bot.features.model.CommandAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.Role
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.github.twitch4j.kotlin.one.bot.getMessageArguments
import com.github.twitch4j.kotlin.one.bot.twitchList

object SetRoleAction : CommandAction(
    "setrole",
    description = "Set role to a specific player by nickname",
    permissions = listOf(MODERATOR),
    executable = fun(event: ChannelMessageEvent) {
        if (event.getMessageArguments("""\s+""")[0] == "reset") {
            TournamentInfo.resetRoles()
            return
        }
        val roles = event.getMessageArguments("""\s+""").zipWithNext()
        roles.forEach { (name, role) ->
            TournamentInfo.players[name]?.currentRole = when (role) {
                "маф" -> Role.BLACK
                "дон" -> Role.HAT
                "шер" -> Role.STAR
                else -> Role.RED
            }
        }

        if (TournamentInfo.getActiveRoles().size == 4) {
            twitchChat.sendMessage(event.channel.name, TournamentInfo.getActiveRoles()
                .map { "${it.currentSlot} - ${it.name} - ${it.currentRole.localized}" }.twitchList
            )
        }
    }
) {
}