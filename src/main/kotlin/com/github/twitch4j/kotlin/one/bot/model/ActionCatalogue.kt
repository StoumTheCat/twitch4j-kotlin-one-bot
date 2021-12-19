package com.github.twitch4j.kotlin.one.bot.model

import com.github.twitch4j.kotlin.one.bot.toTwitchList

object ActionCatalogue {
    val actions: MutableMap<String, ChatAction> = mutableMapOf()

    //todo add some sorting?
    fun getCommandsListForTwitch(): String {
        return actions.entries.map { " !${it.key} - ${it.value.description} " }.toTwitchList()
    }
}