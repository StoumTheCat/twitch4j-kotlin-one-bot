package com.github.twitch4j.kotlin.one.bot.features.model

import com.github.philippheuer.events4j.api.domain.IDisposable
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.Twitch.eventHandler

abstract class AbstractChatAction(override val description: String) : ChatAction {
    private val disposable: IDisposable
    init {
        disposable = eventHandler.onEvent(ChannelMessageEvent::class.java) { this.onChannelMessage(it) }
    }

    override var enabled: Boolean = true
    override fun dispose() {
        disposable.dispose()
    }

    override fun onChannelMessage(event: ChannelMessageEvent) {
        if (enabled && checkPermissions(event) && getFilter(event)) getAction().invoke(event)
    }

    override fun getFilter(event: ChannelMessageEvent): Boolean = true
    override fun checkPermissions(event: ChannelMessageEvent): Boolean = true
}