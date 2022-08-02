package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.features.model.CommandAction
import com.one.bot.features.model.game.TournamentInfo
import com.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.one.bot.getMessageArguments

object SetPlayerStatusAction : CommandAction(
    command = "setstatus",
    description = "Sets voted/killed/removed status for player",
    permissions = listOf(MODERATOR),
    executable = fun(event: ChannelMessageEvent) {
        val args = event.getMessageArguments("""\s+""")
        val status = args[0]
        val slot = args[1]
        val round = args[2]
        TournamentInfo.getPlayerBySlot(slot.toInt()).status = Pair(status, round)
        TournamentInfo.sendGameInfo()
    }
) {
}