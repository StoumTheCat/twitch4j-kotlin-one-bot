package com.one.bot.features.actions.chat

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.chat.BestMoveContest
import com.one.bot.features.model.permissions.PermissionLevel

object BestMoveContestStartAction : CommandWithResponseAction(
    command = "bmstart",
    description = "Starts best move contest",
    permissions = listOf(PermissionLevel.MODERATOR),
    response = fun(event: ChannelMessageEvent): List<String> {
        BestMoveContest.contestRunning = true
        return listOf("Конкурс лх начался!")
    }) {
    init {
        BestMoveContest
        aliases.add("лхстарт")
    }
}