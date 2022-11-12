package com.one.bot.features.actions.chat

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.extractBestMove
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.chat.BestMoveContest
import com.one.bot.features.model.permissions.PermissionLevel
import com.one.bot.messageWithoutCommand

object BestMoveContestResolveAction: CommandWithResponseAction(
command = "bmresolve",
description = "Resolves best move contest",
permissions = listOf(PermissionLevel.MODERATOR),
response = fun(event: ChannelMessageEvent): List<String> {
    val winners = BestMoveContest.resolve(event.messageWithoutCommand.extractBestMove)
    return listOf("Победители в данном раунде: ") + winners
}) {
    init {
        aliases.add("лхчек")
    }
}