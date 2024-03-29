package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.game.Role
import com.one.bot.features.model.game.TournamentInfo
import com.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.one.bot.getMessageArguments
import com.one.bot.ktor.server.Application
import com.one.bot.twitchList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object SetRolesAction : CommandWithResponseAction(
    "setroles",
    permissions = listOf(MODERATOR),
    description = "!роли - List slots starting with sherif, mark don with *",
    response = fun(event: ChannelMessageEvent): List<String> {
        TournamentInfo.resetRoles()
        val roleBySlot = event.getMessageArguments("""\s+""")
        with(TournamentInfo.getCurrentTable()) {
            this[roleBySlot[0].toInt() - 1].currentRole = Role.SHER
            roleBySlot.drop(1).forEach {
                if (it.startsWith("*"))
                    this[it.drop(1).toInt() - 1].currentRole = Role.DON
                else
                    this[it.toInt() - 1].currentRole = Role.BLACK
            }
        }
        runBlocking {
            launch { Application.send("control-panel", event.message) }
        }
        TournamentInfo.sendGameInfo()
        return listOf(
            TournamentInfo.getActiveRoles()
                .map { "${it.currentSlot} - ${it.name} - ${it.currentRole.localized}" }.twitchList
        )
    }
) {
    init {
        aliases.addAll(listOf("роли"))
    }
}