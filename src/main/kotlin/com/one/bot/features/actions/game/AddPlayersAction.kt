package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.command
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.game.TournamentInfo.Player
import com.one.bot.features.model.game.TournamentInfo.players
import com.one.bot.features.model.permissions.PermissionLevel.BROADCASTER
import com.one.bot.getMessageArguments
import com.one.bot.ktor.server.Application
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object AddPlayersAction : CommandWithResponseAction(
    "addplayers",
    description = "Add players with space delimiter",
    permissions = listOf(BROADCASTER),
    response = fun(event: ChannelMessageEvent): List<String> {
        players.clear()
        if (event.command == "addteams") {
            val args = event.getMessageArguments("""\s*\|\s*""")
            val teamPlayersCount = args[0].toInt()
            args.drop(1)
                .chunked(teamPlayersCount + 1)
                .forEach {
                    val teamName = it[0]
                    players.putAll(it.drop(1).associateWith { nickname -> Player(nickname, teamName) }.toMutableMap())
                }
            runBlocking {
                launch { Application.send("control-panel", "!addplayers ${players.keys.joinToString(" ")}") }
            }
        } else {
            val nicknames = event.getMessageArguments("""\s+""")
            players.putAll(nicknames.associateWith { Player(it) }.toMutableMap())
            runBlocking {
                launch { Application.send("control-panel", event.message) }
            }
        }
        return listOf("Current players recorded, list with !players|!p|!игроки|!состав|!баллы")
    }
) {
    init {
        aliases.addAll(listOf("addp", "addteams"))
    }
}