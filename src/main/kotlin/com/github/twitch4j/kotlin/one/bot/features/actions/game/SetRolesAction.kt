package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.Role
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.github.twitch4j.kotlin.one.bot.getMessageArguments
import com.github.twitch4j.kotlin.one.bot.ktor.server.Application
import com.github.twitch4j.kotlin.one.bot.twitchList
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
            this[roleBySlot[0].toInt() - 1].currentRole = Role.STAR
            roleBySlot.drop(1).forEach {
                if (it.startsWith("*"))
                    this[it.drop(1).toInt() - 1].currentRole = Role.HAT
                else
                    this[it.toInt() - 1].currentRole = Role.BLACK
            }
        }
        runBlocking {
            launch { Application.send("control-panel", event.message) }
        }
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