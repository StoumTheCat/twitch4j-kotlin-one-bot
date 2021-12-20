package com.github.twitch4j.kotlin.one.bot.model.actions

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.messageArguments
import com.github.twitch4j.kotlin.one.bot.model.CommandCatalogue
import com.github.twitch4j.kotlin.one.bot.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.model.permissions.PermissionLevels.BROADCASTER

object AddCommandAction : CommandWithResponseAction(
    "addcommand",
    permissions = listOf(BROADCASTER),
    description = "(command_name) | (description) | (response_text) - Use this to add commands with saved text",
    response = fun(event: ChannelMessageEvent): String {
        val args = event.messageArguments
        CommandCatalogue.commands[args[0]]?.dispose()
        CommandWithResponseAction(command = args[0], description = args[1], response = { args[2] })
        return "Command !${args[0]} has been added, check with !commands"
    }
) {
    init {
        aliases.add("addc")
    }
}