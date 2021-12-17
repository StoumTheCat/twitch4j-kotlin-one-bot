package com.github.twitch4j.kotlin.one.bot.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.model.permissions.Permission

abstract class AbstractPermissibleChatAction(protected val permissions: List<Permission>) :
    AbstractChatAction() {
    override fun checkPermissions(event: ChannelMessageEvent): Boolean {
        return permissions.all { p -> p.check(event) }
    }
}