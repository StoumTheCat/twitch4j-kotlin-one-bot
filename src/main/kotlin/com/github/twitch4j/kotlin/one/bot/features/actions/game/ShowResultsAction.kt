package com.github.twitch4j.kotlin.one.bot.features.actions.game

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import com.github.twitch4j.kotlin.one.bot.twitchList

object ShowResultsAction : CommandWithResponseAction(
    "results",
    description = "!players !p !игроки !состав !баллы !б - показать состав и результаты",
    response = fun(_: ChannelMessageEvent): String {
        return TournamentInfo.getResultLines().twitchList
    }
) {
    init {
        aliases.addAll(listOf("players", "p", "игроки", "состав", "баллы", "б"))
    }
}