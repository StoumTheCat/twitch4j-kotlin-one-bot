package com.github.twitch4j.kotlin.one.bot.features.actions

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.Twitch.twitchChat
import com.github.twitch4j.kotlin.one.bot.command
import com.github.twitch4j.kotlin.one.bot.features.model.CommandAction
import com.github.twitch4j.kotlin.one.bot.features.model.permissions.PermissionLevel
import com.github.twitch4j.kotlin.one.bot.messageWithoutCommand
import com.github.twitch4j.kotlin.one.bot.obs.OBSHelper

object OBSAction : CommandAction(
    "obs",
    description = "Scene switcher",
    permissions = listOf(PermissionLevel.MODERATOR),
    executable = fun(event: ChannelMessageEvent) {
        when (event.command) {
            "obs" -> OBSHelper.setScene(event.messageWithoutCommand) {
                if (it.error.isNotBlank()) {
                    twitchChat.sendMessage(event.channel.name, it.error)
                }
            }
            "day", "night", "game" -> OBSHelper.switchDayNight()
            "guest1" -> OBSHelper.setSceneOneGuest()
            "guest2" -> OBSHelper.setSceneTwoGuests()
            "showresults" -> OBSHelper.setSceneResults()
            "solo" -> OBSHelper.setSceneSolo()
        }
    }
) {
    init {
        aliases.addAll(listOf("day", "night", "guest1", "guest2", "solo", "showresults", "game"))
    }

}