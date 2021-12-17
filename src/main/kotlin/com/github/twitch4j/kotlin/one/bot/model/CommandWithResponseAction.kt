package com.github.twitch4j.kotlin.one.bot.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

class CommandWithResponseAction(command: String, response: (ChannelMessageEvent) -> String) :
    CommandAction(command, response) {

    private val commandWithResponse = fun(event: ChannelMessageEvent) {
        twitchChat.sendMessage(event.channel.name, response.invoke(event))
    }

    override fun getAction(): (ChannelMessageEvent) -> Unit {
        return commandWithResponse
    }
}