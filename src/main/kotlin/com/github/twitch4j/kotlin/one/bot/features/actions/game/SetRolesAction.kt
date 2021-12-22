package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.Role
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.github.twitch4j.kotlin.one.bot.getMessageArguments
import com.github.twitch4j.kotlin.one.bot.twitchList

object SetRolesAction : CommandWithResponseAction(
    "setroles",
    permissions = listOf(MODERATOR),
    description = "!роли - List slots starting with sherif, mark don with *",
    response = fun(event: ChannelMessageEvent): String {
        val roleBySlot = event.getMessageArguments("""\s*""")
        with(TournamentInfo.getCurrentTable()) {
            this[roleBySlot[0].toInt()].currentRole = Role.STAR
            roleBySlot.drop(1).forEach {
                if (it.startsWith("*"))
                    this[it.drop(1).toInt()].currentRole = Role.HAT
                else
                    this[it.toInt()].currentRole = Role.BLACK
            }
        }
        return "Roles has been recorded:\n ${
            TournamentInfo.getActiveRoles()
                .map { "${it.currentSlot} - ${it.name} - ${it.currentRole.localized}" }.twitchList
        }"
    }
) {
    init {
        aliases.addAll(listOf("роли"))
    }
}