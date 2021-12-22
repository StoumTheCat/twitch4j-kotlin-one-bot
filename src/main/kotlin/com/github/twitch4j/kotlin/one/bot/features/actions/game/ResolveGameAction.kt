package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel.MODERATOR
import com.github.twitch4j.kotlin.one.bot.getMessageArguments
import com.github.twitch4j.kotlin.one.bot.twitchList

object ResolveGameAction : CommandWithResponseAction(
    "win",
    permissions = listOf(MODERATOR),
    description = "(red/black) (first killed slot) (best move) Resolves game result with set parameters",
    response = fun(event: ChannelMessageEvent): String {
        val args = event.getMessageArguments("""\s+""")
        val winner = args[0]
        val firstKilled = args[1].toInt()
        val bestMove = args.drop(2).map { it.toInt() }
        TournamentInfo.resolveRound(winner, firstKilled, bestMove)
        return TournamentInfo.getResultLines().twitchList
    }
) {
    init {
        aliases.addAll(listOf("победа"))
    }
}