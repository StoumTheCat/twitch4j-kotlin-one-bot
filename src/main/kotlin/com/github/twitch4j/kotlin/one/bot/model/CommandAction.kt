package com.github.twitch4j.kotlin.one.bot.model

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

open class CommandAction(private val command: String, private val executable: (ChannelMessageEvent) -> Any) :
    AbstractChatAction() {

    override fun getAction(): (ChannelMessageEvent) -> Any {
        return executable
    }

    override fun getFilter(event: ChannelMessageEvent): Boolean =
        super.getFilter(event)
                && event.message.startsWith("!${command}")

}