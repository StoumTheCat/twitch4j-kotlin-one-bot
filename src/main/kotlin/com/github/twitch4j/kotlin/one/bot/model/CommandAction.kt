package com.github.twitch4j.kotlin.one.bot.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.model.permissions.Permission
import com.github.twitch4j.kotlin.one.bot.model.permissions.PermissionLevels.EVERYONE

open class CommandAction(
    private val command: String,
    permissions: List<Permission> = listOf(EVERYONE),
    private val executable: (ChannelMessageEvent) -> Any
) : AbstractPermissibleChatAction(permissions) {

    override fun getAction(): (ChannelMessageEvent) -> Any {
        return executable
    }

    override fun getFilter(event: ChannelMessageEvent): Boolean =
        super.getFilter(event)
                && event.message.startsWith("!${command}")

}