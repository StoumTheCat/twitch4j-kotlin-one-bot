package com.github.twitch4j.kotlin.one.bot.features

import com.github.twitch4j.kotlin.one.bot.features.actions.AddCommandAction
import com.github.twitch4j.kotlin.one.bot.features.actions.CommandsListAction
import com.github.twitch4j.kotlin.one.bot.features.actions.game.AddPlayersAction
import com.github.twitch4j.kotlin.one.bot.features.actions.game.SetRolesAction
import com.github.twitch4j.kotlin.one.bot.features.actions.game.SetTableAction
import com.github.twitch4j.kotlin.one.bot.features.actions.game.ShowResultsAction

object Features {
    init {
        CommandsListAction
        AddCommandAction
        AddPlayersAction
        ShowResultsAction
        SetRolesAction
        SetTableAction
    }
}