package com.one.bot.features

import com.one.bot.features.actions.AddCommandAction
import com.one.bot.features.actions.CommandsListAction
import com.one.bot.features.actions.OBSAction
import com.one.bot.features.actions.chat.*
import com.one.bot.features.actions.game.*

object Features {
    init {
        //CommandsListAction
        //AddCommandAction
        AddPlayersAction
        //ShowResultsAction
        SetRolesAction
        SetTableAction
        ResolveGameAction
        AddPointsAction
        SetRoleAction
        OBSAction
        //ShowStatsAction
        //SetStatAction
        AddCheckAction
        SetPlayerStatusAction
        BestMoveContestStartAction
        BestMoveContestEndAction
        BestMoveSubAction
        BestMoveContestResolveAction
        BestMoveContestResultsAction
    }
}