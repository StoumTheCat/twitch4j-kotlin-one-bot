package com.github.twitch4j.kotlin.one.bot.model.permissions

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.common.enums.CommandPermission

enum class PermissionLevels : Permission {

    EVERYONE {
        override fun check(event: ChannelMessageEvent): Boolean {
            return checkPermission(event, CommandPermission.EVERYONE)
        }
    },

    BROADCASTER {
        override fun check(event: ChannelMessageEvent): Boolean {
            return checkPermission(event, CommandPermission.BROADCASTER)
        }
    },

    SUB_AND_VIP {
        override fun check(event: ChannelMessageEvent): Boolean {
            return checkPermission(event, CommandPermission.SUBSCRIBER, CommandPermission.VIP)
        }
    },

    MODERATOR {
        override fun check(event: ChannelMessageEvent): Boolean {
            return checkPermission(event, CommandPermission.MODERATOR)
        }
    };

    fun checkPermission(event: ChannelMessageEvent, vararg commandPermission: CommandPermission) =
        event.permissions.any { p -> commandPermission.contains(p) }
}