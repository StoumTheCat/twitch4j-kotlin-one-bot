package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo

object ShowStatsAction : CommandWithResponseAction(
    "stats",
    description = "Show stats for the tournament",
    response = {
        TournamentInfo.getStatsLines()
    }
) {
    init {
        aliases.addAll(listOf("стат", "победы", "отстрелы"))
    }
}