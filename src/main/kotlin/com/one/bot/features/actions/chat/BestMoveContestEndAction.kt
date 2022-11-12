package com.one.bot.features.actions.chat

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.chat.BestMoveContest
import com.one.bot.features.model.permissions.PermissionLevel

object BestMoveContestEndAction : CommandWithResponseAction(
    command = "bmend",
    description = "Ends best move contest",
    permissions = listOf(PermissionLevel.MODERATOR),
    response = fun(event: ChannelMessageEvent): List<String> {
        BestMoveContest.contestRunning = false
        return listOf("Приняты лучшие ходы:") +
                BestMoveContest.currentRound.entries.map { "[${it.key} - ${it.value.joinToString(", ")}] "  }.toList()
    }) {
    init {
        BestMoveContest
        aliases.add("лхстоп")
    }
}