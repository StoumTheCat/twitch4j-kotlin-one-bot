package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.command
import com.one.bot.features.model.CommandAction
import com.one.bot.features.model.game.TournamentInfo
import com.one.bot.features.model.permissions.PermissionLevel
import com.one.bot.getMessageArguments

object SetStatAction : CommandAction(
    command = "setstat",
    description = "Sets current/mwt statistic of a player",
    permissions = listOf(PermissionLevel.MODERATOR),
    executable = fun(event: ChannelMessageEvent) {
        if (event.command == "setstat") {
            val args = event.getMessageArguments("""\s+""")
            val nickname = args[0]
            val statName = args[1]
            val wins = args[2].toInt()
            val total = args[3].toInt()

            TournamentInfo.players[nickname]?.stat?.set(statName, Pair(wins, total))

            TournamentInfo.sendGameInfo()
        } else {//mwt stat
            val args = event.getMessageArguments("""\s+""")
            val nickname = args[0]
            val statName = args[1]
            val mwtStat = args[2]

            TournamentInfo.players[nickname]?.mwtStat?.set(statName, mwtStat)

            TournamentInfo.sendGameInfo()
        }
    }
) {
    init {
        aliases.addAll(listOf("setmwt"))
    }
}