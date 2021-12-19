package com.github.twitch4j.kotlin.one.bot

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.philippheuer.events4j.simple.SimpleEventHandler
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.kotlin.one.bot.model.ChatAction
import com.github.twitch4j.kotlin.one.bot.model.CommandAction
import com.github.twitch4j.kotlin.one.bot.model.CommandWithResponseAction
import com.github.twitch4j.kotlin.one.bot.model.actions.CommandsListAction
import com.github.twitch4j.kotlin.one.bot.model.permissions.PermissionLevels
import kotlin.system.exitProcess

object Bot {

    /** Holds the configuration */
    private val configuration: Configuration =
        loadConfiguration()

    /** Holds the client */
    private val twitchClient: TwitchClient = createClient()

    /** Register all features */
    fun registerFeatures() {
        val eventHandler = twitchClient.eventManager.getEventHandler(SimpleEventHandler::class.java)
        val test = CommandAction("test", listOf(PermissionLevels.BROADCASTER)) {
            println(it.message)
        }
        val test2: ChatAction = CommandAction("test2") {
            println("this is test 2 ${it.message}")
        }
        val respond: ChatAction = CommandWithResponseAction("respond") {
            "This is a response for your message ${it.message}"
        }
        println(test.description)
        CommandsListAction
        /*WriteChannelChatToConsole(eventHandler)
        ChannelNotificationOnFollow(eventHandler)
        ChannelNotificationOnSubscription(eventHandler)
        ChannelNotificationOnDonation(eventHandler)*/
    }

    /** Start the bot, connecting it to every channel specified in the configuration */
    fun start() {
        // Connect to all channels
        for (channel in configuration.channels) {
            twitchClient.chat.joinChannel(channel)
        }
    }

    /** Load the configuration from the config.yaml file */
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

    /** Create the client */
    private fun createClient(): TwitchClient {
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

        return clientBuilder.build()
    }

}