package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.diff
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.game.TournamentInfo
import com.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.one.bot.getMessageArguments
import com.one.bot.ktor.server.Application
import com.one.bot.twitchList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object SetTableAction : CommandWithResponseAction(
    "settable",
    permissions = listOf(MODERATOR),
    description = "!стол List players by nickname with space delimiter",
    response = fun(event: ChannelMessageEvent): List<String> {
        TournamentInfo.resetPlayers()
        val players = event.getMessageArguments("""\s+""")
        //todo refactor this abominable htonь
        with(players.map { it.toLowerCase() }) {
            if (TournamentInfo.players.values.map { it.name.toLowerCase() }.diff(this).isNotEmpty()) {
                return listOf(
                    "Following players not found: ${
                        TournamentInfo.players.values.map { it.name.toLowerCase() }.diff(this).twitchList
                    }"
                )
            }

            players.withIndex().forEach { TournamentInfo.players[it.value]?.currentSlot = it.index + 1 }
            runBlocking {
                launch {
                    Application.send("control-panel", event.message)
                }
            }
            TournamentInfo.sendGameInfo()
            return listOf("Table has been set")
            //return TournamentInfo.getCurrentTable().map { "${it.currentSlot} - ${it.name}" }
        }
    }
) {
    init {
        aliases.addAll(listOf("стол"))
    }
}