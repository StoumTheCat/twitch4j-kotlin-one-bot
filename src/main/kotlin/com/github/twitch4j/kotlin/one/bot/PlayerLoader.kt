package com.github.twitch4j.kotlin.one.bot

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.twitch4j.kotlin.one.bot.features.model.game.TournamentInfo
import java.io.File

object PlayerLoader {
    fun loadWithStats(filename: String) {

        val file = File("/Users/andrey.tkach/IdeaProjects/twitch4j-kotlin-one-bot/src/main/resources/NorthCup.txt")
        val rows: List<Map<String, String>> = csvReader().readAllWithHeader(file)
        rows.forEach { row ->
            val player = TournamentInfo.Player(row["nick"]!!)
            if (row["id"] != "-1") {
                player.mwtStat.putAll(row.filterNot { it.key == "nick" || it.key == "id" })
            }
            TournamentInfo.players.put(player.name, player)
        }
        println(rows)
    }
}