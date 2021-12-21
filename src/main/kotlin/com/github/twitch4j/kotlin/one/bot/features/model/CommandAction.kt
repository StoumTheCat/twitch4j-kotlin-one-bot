package com.github.twitch4j.kotlin.one.bot.features.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.command
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.Permission
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevels.EVERYONE

@Suppress("LeakingThis")
open class CommandAction(
    private val command: String,
    permissions: List<Permission> = listOf(EVERYONE),
    description: String = "Default command action",
    private val executable: (ChannelMessageEvent) -> Any
) : AbstractPermissibleChatAction(permissions, description) {
    init {
        CommandCatalogue.commands[command] = this
    }

    var aliases: MutableList<String> = mutableListOf()

    override fun dispose() {
        super.dispose()
        CommandCatalogue.commands.remove(command)
    }

    override fun getAction(): (ChannelMessageEvent) -> Any {
        return executable
    }

    override fun getFilter(event: ChannelMessageEvent): Boolean =
        super.getFilter(event)
                && (event.command == command || aliases.any { event.command == it })

}