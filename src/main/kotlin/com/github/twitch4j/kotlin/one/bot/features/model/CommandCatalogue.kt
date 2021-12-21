package com.github.twitch4j.kotlin.one.bot.features.model

import com.github.twitch4j.kotlin.one.bot.twitchList

object CommandCatalogue {
    val commands: MutableMap<String, CommandAction> = mutableMapOf()

    //todo add some sorting?
    fun getCommandsListForTwitch(): String {
        return commands.entries.map { " !${it.key} - ${it.value.description} " }.twitchList
    }
}