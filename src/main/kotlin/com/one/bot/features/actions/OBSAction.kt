package com.one.bot.features.actions

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.Twitch.twitchChat
import com.one.bot.command
import com.one.bot.features.model.CommandAction
import com.one.bot.features.model.permissions.PermissionLevel
import com.one.bot.messageWithoutCommand
import com.one.bot.obs.OBSHelper

object OBSAction : CommandAction(
    "obs",
    description = "Scene switcher",
    permissions = listOf(PermissionLevel.MODERATOR),
    executable = fun(event: ChannelMessageEvent) {
        when (event.command) {
            /*"obs" -> OBSHelper.setScene(event.messageWithoutCommand) {
                if (it.error.isNotBlank()) {
                    twitchChat.sendMessage(event.channel.name, it.error)
                }
            }*/
            "day", "night", "game" -> OBSHelper.switchDayNight()
            "guest1" -> OBSHelper.setSceneOneGuest()
            "guest2" -> OBSHelper.setSceneTwoGuests()
            "showresults" -> OBSHelper.setSceneResults()
            "solo" -> OBSHelper.setSceneSolo()
            "setsource" -> OBSHelper.updateBrowserSourceUrl(event.messageWithoutCommand)
        }
    }
) {
    init {
        aliases.addAll(listOf("day", "night", "guest1", "guest2", "solo", "showresults", "game", "setsource"))
    }

}