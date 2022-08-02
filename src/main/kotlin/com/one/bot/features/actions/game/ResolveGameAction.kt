package com.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.game.TournamentInfo
import com.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.one.bot.getMessageArguments
import com.one.bot.ktor.server.Application
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object ResolveGameAction : CommandWithResponseAction(
    "win",
    permissions = listOf(MODERATOR),
    description = "(red/black) (first killed slot) (best move) Resolves game result with set parameters",
    response = fun(event: ChannelMessageEvent): List<String> {
        val args = event.getMessageArguments("""\s+""")
        val winner = args[0]
        val firstKilled = args[1].toInt()
        val bestMove = args.drop(2).map { it.toInt() }
        TournamentInfo.resolveRound(winner, firstKilled, bestMove)
        val teamResults = TournamentInfo.getTeamResults()
        val resultsForTablePanel =
            teamResults.map { "${it.first} | ${it.second}" }.reduce { prev, next -> "$prev | $next" }
        runBlocking {
            launch { Application.send("table-control", "!table | $resultsForTablePanel") }
        }
        return TournamentInfo.getTeamResultsLines()
    }
) {
    init {
        aliases.addAll(listOf("победа"))
    }
}