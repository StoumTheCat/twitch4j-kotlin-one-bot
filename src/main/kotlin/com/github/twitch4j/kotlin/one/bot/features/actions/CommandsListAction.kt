package com.github.twitch4j.kotlin.one.bot.features.actions

import com.github.twitch4j.kotlin.one.bot.features.model.CommandCatalogue
import com.github.twitch4j.kotlin.one.bot.features.model.CommandWithResponseAction

object CommandsListAction : CommandWithResponseAction(
    "commands",
    description = "Shows list of commands",
    response = { CommandCatalogue.getCommandsListForTwitch() }
) {
    init {
        aliases.add("c")
    }
}