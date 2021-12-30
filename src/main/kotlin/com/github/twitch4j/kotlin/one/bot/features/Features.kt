package com.github.twitch4j.kotlin.one.bot.features

import com.github.twitch4j.kotlin.one.bot.features.actions.AddCommandAction
import com.github.twitch4j.kotlin.one.bot.features.actions.CommandsListAction
import com.github.twitch4j.kotlin.one.bot.features.actions.game.*

object Features {
    init {
        CommandsListAction
        AddCommandAction
        AddPlayersAction
        ShowResultsAction
        SetRolesAction
        SetTableAction
        ResolveGameAction
        AddPointsAction
        SetRoleAction
    }
}