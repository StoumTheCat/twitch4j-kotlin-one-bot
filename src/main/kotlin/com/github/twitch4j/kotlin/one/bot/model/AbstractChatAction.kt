package com.github.twitch4j.kotlin.one.bot.model

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.TwitchChat
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.github.twitch4j.kotlin.one.bot.Configuration
import kotlin.system.exitProcess

abstract class AbstractChatAction {
    init {
        eventHandler.onEvent(ChannelMessageEvent::class.java) { this.onChannelMessage(it) }
    }

    protected companion object {
        //move to some kind of util?
        private fun loadConfiguration(): Configuration {
            val config: Configuration
            val classloader = Thread.currentThread().contextClassLoader
            val inputStream = classloader.getResourceAsStream("config.yaml")
            val mapper = ObjectMapper(YAMLFactory())

            try {
                config = mapper.readValue(inputStream, Configuration::class.java)
            } catch (ex: Exception) {
                println("Unable to load configuration... Exiting")
                exitProcess(1)
            }

            return config
        }

        val twitchClient: TwitchClient

        init {
            val configuration: Configuration = loadConfiguration()
            var clientBuilder = TwitchClientBuilder.builder()

            //region Chat related configuration
            val credential = OAuth2Credential(
                "twitch",
                configuration.credentials["irc"]
            )

            clientBuilder = clientBuilder
                .withChatAccount(credential)
                .withEnableChat(true)
            //endregion

            //region Api related configuration
            clientBuilder = clientBuilder
                .withClientId(configuration.api["twitch_client_id"])
                .withClientSecret(configuration.api["twitch_client_secret"])
                .withEnableHelix(true)
                /*
                     * GraphQL has a limited support
                     * Don't expect a bunch of features enabling it
                     */
                .withEnableGraphQL(true)
                /*
                     * Kraken is going to be deprecated
                     * see : https://dev.twitch.tv/docs/v5/#which-api-version-can-you-use
                     * It is only here so you can call methods that are not (yet)
                     * implemented in Helix
                     */
                .withEnableKraken(true)
            //endregion

            // Build the client out of the configured builder

            twitchClient = clientBuilder.build()
        }

        val eventHandler: SimpleEventHandler = twitchClient.eventManager.getEventHandler(SimpleEventHandler::class.java)
        val twitchChat: TwitchChat = twitchClient.chat
    }

    private fun onChannelMessage(event: ChannelMessageEvent) {
        if (getFilter(event)) getAction().invoke(event)
    }

    protected abstract fun getAction(): (ChannelMessageEvent) -> Any
    protected open fun getFilter(event: ChannelMessageEvent): Boolean = true
}