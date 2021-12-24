package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo.Player
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel.BROADCASTER
import com.github.twitch4j.kotlin.one.bot.getMessageArguments
import com.github.twitch4j.kotlin.one.bot.ktor.server.Application
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object AddPlayersAction : CommandWithResponseAction(
    "addplayers",
    description = "Add players with space delimiter",
    permissions = listOf(BROADCASTER),
    response = fun(event: ChannelMessageEvent): String {
        val players = event.getMessageArguments("""\s+""")
        TournamentInfo.players.clear()
        TournamentInfo.players.putAll(players.associateWith { Player(it) }.toMutableMap())
        runBlocking {
            launch { Application.send("control-panel", event.message) }
        }
        return "Current players recorded, list with !players|!p|!игроки|!состав|!баллы"
    }
) {
    init {
        aliases.addAll(listOf("addp"))
    }
}