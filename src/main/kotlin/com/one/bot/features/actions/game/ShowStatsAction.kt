package com.one.bot.features.actions.game

import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.game.TournamentInfo

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