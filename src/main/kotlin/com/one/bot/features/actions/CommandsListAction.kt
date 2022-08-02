package com.one.bot.features.actions

import com.one.bot.Twitch.twitchChat
import com.one.bot.features.model.CommandAction
import com.one.bot.features.model.CommandCatalogue
import com.one.bot.sendAsList

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