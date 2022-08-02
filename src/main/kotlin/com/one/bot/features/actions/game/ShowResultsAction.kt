package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.command
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.game.TournamentInfo
import com.one.bot.ktor.server.Application
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object ShowResultsAction : CommandWithResponseAction(
    "results",
    description = "!players !p !игроки !состав !баллы !б - показать состав и результаты",
    response = fun(event: ChannelMessageEvent): List<String> {
        return if (event.command == "команды") {
            val teamResults = TournamentInfo.getTeamResults()
            val resultsForTablePanel =
                teamResults.map { "${it.first} | ${it.second}" }.reduce { prev, next -> "$prev | $next" }
            runBlocking {
                launch { Application.send("table-control", "!table | $resultsForTablePanel") }
            }
            TournamentInfo.getTeamResultsLines()
        } else {
            /*val personalResults = TournamentInfo.getResults()
            val resultsForTablePanel = personalResults.map { "${it.name} | ${it.points + it.ciPoints}" }.reduce { prev, next -> "$prev | $next"}
            runBlocking {
                launch { Application.send("table-control", "!table | $resultsForTablePanel") }
            }*/
            TournamentInfo.getResultLines()
        }
    }
) {
    init {
        aliases.addAll(listOf("players", "p", "игроки", "состав", "баллы", "б", "команды"))
    }
}