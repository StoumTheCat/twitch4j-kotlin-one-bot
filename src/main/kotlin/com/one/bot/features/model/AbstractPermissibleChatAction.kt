package com.one.bot.features.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.features.model.permissions.Permission

abstract class AbstractPermissibleChatAction(
    protected val permissions: List<Permission>,
    description: String = "Abstract permissible chat action"
) :
    AbstractChatAction(description) {
    override fun checkPermissions(event: ChannelMessageEvent): Boolean {
        return permissions.all { p -> p.check(event) }
    }
}