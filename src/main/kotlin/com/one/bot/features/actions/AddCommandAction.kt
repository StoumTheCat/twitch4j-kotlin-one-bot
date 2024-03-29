package com.one.bot.features.actions

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.features.model.CommandCatalogue
import com.one.bot.features.model.CommandWithResponseAction
import com.one.bot.features.model.permissions.PermissionLevel.BROADCASTER
import com.one.bot.getMessageArguments

object AddCommandAction : CommandWithResponseAction(
    "addcommand",
    permissions = listOf(BROADCASTER),
    description = "(command_name) | (description) | (response_text) - Use this to add commands with saved text",
    response = fun(event: ChannelMessageEvent): List<String> {
        val args = event.getMessageArguments()
        CommandCatalogue.commands[args[0]]?.dispose()
        CommandWithResponseAction(command = args[0], description = args[1]) { listOf(args[2]) }
        return listOf("Command !${args[0]} has been added, check with !commands")
    }
) {
    init {
        aliases.add("addc")
    }
}