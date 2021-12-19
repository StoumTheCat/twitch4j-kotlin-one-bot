package com.github.twitch4j.kotlin.one.bot.model.actions

import com.github.twitch4j.kotlin.one.bot.model.CommandCatalogue
import com.github.twitch4j.kotlin.one.bot.model.CommandWithResponseAction

object CommandsListAction : CommandWithResponseAction(
    "commands",
    description = "Shows list of commands",
    response = { CommandCatalogue.getCommandsListForTwitch() }
)