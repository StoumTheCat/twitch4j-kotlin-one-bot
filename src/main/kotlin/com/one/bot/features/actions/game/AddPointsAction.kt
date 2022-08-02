package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.game.TournamentInfo
import com.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.one.bot.getMessageArguments
import com.one.bot.roundUp

object AddPointsAction : CommandWithResponseAction(
    "addpoints",
    permissions = listOf(MODERATOR),
    description = "!доп !points Add points to specific player(s)",
    response = fun(event: ChannelMessageEvent): List<String> {
        val points = event.getMessageArguments("""\s+""").zipWithNext()
        points.forEach {
            TournamentInfo.players[it.first]?.points =
                (TournamentInfo.players[it.first]?.points?.plus(it.second.toDouble())!!).roundUp()
        }
        return TournamentInfo.getResultLines()
    }
) {
    init {
        aliases.addAll(listOf("доп", "points"))
    }
}