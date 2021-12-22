package com.github.twitch4j.kotlin.one.bot.features.model

object CommandCatalogue {
    val commands: MutableMap<String, CommandAction> = mutableMapOf()

    //todo add some sorting?
    fun getCommandsListForTwitch(): List<String> {
        return commands.entries.map { " !${it.key} - ${it.value.description} " }
    }
}