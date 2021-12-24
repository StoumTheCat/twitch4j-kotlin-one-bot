package com.github.twitch4j.kotlin.one.bot.features.actions

import com.github.twitch4j.kotlin.one.bot.Twitch.twitchChat
import com.github.twitch4j.kotlin.one.bot.features.model.CommandAction
import com.github.twitch4j.kotlin.one.bot.features.model.CommandCatalogue
import com.github.twitch4j.kotlin.one.bot.sendAsList

object CommandsListAction : CommandAction(
    "commands",
    description = "Shows list of commands",
    executable = { event ->
        twitchChat.sendAsList(event.channel.name, CommandCatalogue.getCommandsListForTwitch())
    }
) {
    init {
        aliases.add("c")
    }
}