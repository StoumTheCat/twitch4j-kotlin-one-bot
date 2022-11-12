package com.one.bot

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.one.bot.features.Features
import kotlin.system.exitProcess

object Bot {

    /** Holds the configuration */
    private val configuration: com.one.bot.Configuration =
        com.one.bot.Bot.loadConfiguration()

    /** Holds the client */
    private val twitchClient: TwitchClient = com.one.bot.Bot.createClient()

    /** Register all features */
    fun registerFeatures() {
        Features
    }

    /** Start the bot, connecting it to every channel specified in the configuration */
    fun start() {
        // Connect to all channels
        for (channel in com.one.bot.Bot.configuration.channels) {
            com.one.bot.Bot.twitchClient.chat.joinChannel(channel)
        }

        twitchClient.clientHelper.enableStreamEventListener(configuration.channels)
    }

    /** Load the configuration from the config.yaml file */
    private fun loadConfiguration(): com.one.bot.Configuration {
        val config: com.one.bot.Configuration
        val classloader = Thread.currentThread().contextClassLoader
        val inputStream = classloader.getResourceAsStream("config.yaml")
        val mapper = ObjectMapper(YAMLFactory())

        try {
            config = mapper.readValue(inputStream, com.one.bot.Configuration::class.java)
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
            com.one.bot.Bot.configuration.credentials["irc"]
        )

        clientBuilder = clientBuilder
            .withChatAccount(credential)
            .withEnableChat(true)
        //endregion

        //region Api related configuration
        clientBuilder = clientBuilder
            .withClientId(com.one.bot.Bot.configuration.api["twitch_client_id"])
            .withClientSecret(com.one.bot.Bot.configuration.api["twitch_client_secret"])
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
            //.withEnableKraken(true)
        //endregion

        // Build the client out of the configured builder

        return clientBuilder.build()
    }

}
