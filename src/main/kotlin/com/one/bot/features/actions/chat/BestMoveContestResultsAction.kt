package com.one.bot.features.actions.chat

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.extractBestMove
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.chat.BestMoveContest
import com.one.bot.features.model.permissions.PermissionLevel
import com.one.bot.messageWithoutCommand

object BestMoveContestResultsAction: CommandWithResponseAction(
    command = "bmresults",
    description = "Resolves best move contest",
    permissions = listOf(PermissionLevel.MODERATOR),
    response = fun(event: ChannelMessageEvent): List<String> {
        val winners = BestMoveContest.participants.entries.map { "${it.key} - ${it.value}"  }.toList()
        return listOf("Сумма очков: ") + winners
    }) {
    init {
        aliases.add("лхрез")
    }
}