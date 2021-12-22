package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.github.twitch4j.kotlin.one.bot.getMessageArguments
import com.github.twitch4j.kotlin.one.bot.twitchList

object AddPointsAction : CommandWithResponseAction(
    "addpoints",
    permissions = listOf(MODERATOR),
    description = "!доп !points Add points to specific player(s)",
    response = fun(event: ChannelMessageEvent): String {
        val points = event.getMessageArguments("""\s+""").zipWithNext()
        points.forEach {
            TournamentInfo.players[it.first]?.points =
                TournamentInfo.players[it.first]?.points?.plus(it.second.toDouble())!!
        }
        return TournamentInfo.getResultLines().twitchList
    }
) {
    init {
        aliases.addAll(listOf("доп", "points"))
    }
}