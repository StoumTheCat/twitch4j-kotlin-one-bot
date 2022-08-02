package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.Twitch.twitchChat
import com.one.bot.features.model.CommandAction
import com.one.bot.features.model.game.Role
import com.one.bot.features.model.game.TournamentInfo
import com.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.one.bot.getMessageArguments
import com.one.bot.twitchList

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
                "дон" -> Role.DON
                "шер" -> Role.SHER
                else -> Role.RED
            }
        }
        TournamentInfo.sendGameInfo()
        if (TournamentInfo.getActiveRoles().size == 4) {
            twitchChat.sendMessage(event.channel.name, TournamentInfo.getActiveRoles()
                .map { "${it.currentSlot} - ${it.name} - ${it.currentRole.localized}" }.twitchList
            )
        }
    }
) {
}