package com.github.twitch4j.kotlin.one.bot.features.model.game

import com.github.twitch4j.kotlin.one.bot.ktor.server.Application
import com.github.twitch4j.kotlin.one.bot.roundUp
import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.math.RoundingMode
import kotlin.math.roundToInt

object TournamentInfo {
    var numberOfGames = 12
    var currentGame = 1
    var redWins = 0
    var blackWins = 0

    class Player(val name: String, val team: String = "") {
        var currentTable: Int = 0
        var currentSlot: Int = 0
        var currentRole: Role = Role.RED
        var points: Double = 0.0
        var firstDead: Int = 0
        var firstDeadLoss: Int = 0
        var ciPoints: Double = 0.0
        var stat: MutableMap<String, Pair<Int, Int>> = mutableMapOf()
        var mwtStat: MutableMap<String, String> = mutableMapOf()
        var status: Pair<String, String>? = null
        var checks: MutableList<Pair<String, String>> = mutableListOf()

        fun recalculateCI(gamesCount: Int) {
            val b = gamesCount.times(0.4).roundToInt()
            val ci = if (firstDead <= b) firstDead * 0.4 / b else 0.4

            ciPoints = (ci * firstDeadLoss).toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
        }

        fun reset() {
            currentTable = 0
            currentSlot = 0
            currentRole = Role.RED
            status = null
            checks.clear()
        }

        fun toPlayerInfo(): GameInfo.PlayerInfo {
            val info = GameInfo.PlayerInfo(name, currentRole.name.toLowerCase())
            val statCurrent = this.stat.mapValues { "${it.value.first}/${it.value.second}" }
            info.stat =
                statCurrent.keys.associateWith { Pair(this.mwtStat[it] ?: "", statCurrent[it] ?: "") }.toMutableMap()
            info.checks = this.checks
            info.status = this.status
            return info
        }
    }

    val players: MutableMap<String, Player> = mutableMapOf()
    var gameInfo: GameInfo = GameInfo()
    val gameInfoHistory: MutableList<GameInfo> = mutableListOf()

    fun getPlayerBySlot(slot: Int): Player {
        return players.values.first { it.currentSlot == slot }
    }

    fun resetRoles() {
        players.values.forEach { it.currentRole = Role.RED }
        sendGameInfo()
    }

    fun getCurrentTable(tableNumber: Int = 0): Array<Player> {
        return players.values
            .filter { it.currentTable == tableNumber }
            .filter { it.currentSlot != 0 }
            .sortedBy { it.currentSlot }.toTypedArray()
    }

    fun getResults(): List<Player> {
        return players.values.sortedByDescending { it.points + it.ciPoints }
    }

    fun getTeamResults(): List<Pair<String, Double>> {
        return players.values.groupBy { it.team }
            .entries.associate { it.key to it.value.sumOf { player -> player.points + player.ciPoints } }
            .toList().sortedByDescending { (_, points) -> points }
    }

    fun getResultLines(): List<String> {
        return getResults().withIndex()
            .map { "${it.index + 1}. ${it.value.name}${if (it.value.team.isNotBlank()) " - " + it.value.team else ""} - ${it.value.points + it.value.ciPoints}" }
    }

    fun getTeamResultsLines(): List<String> {
        return getTeamResults().withIndex()
            .map { "${it.index + 1}. ${it.value.first} - ${it.value.second}" }
    }

    fun getStatsLines(): List<String> {
        val stats = mutableListOf(
            "Красные - ${redWins}, Черные - $blackWins",
            "ПУ:"
        )
        stats.addAll(players.values.filter { it.firstDead > 0 }.map { "${it.name} - ${it.firstDead}" })

        return stats
    }

    fun getActiveRoles(tableNumber: Int = 0): List<Player> {
        return getCurrentTable(tableNumber).filter { it.currentRole != Role.RED }
    }

    fun resolveRound(winner: String, firstDead: Int, bestMove: List<Int>, table: Int = 0) {
        gameInfo.win = winner
        if (winner == "red") {
            redWins++
        } else {
            blackWins++
        }
        with(getCurrentTable(table)[firstDead - 1]) {
            this.firstDead++
            if (this.currentRole.team.equals(Role.RED.team, ignoreCase = true)) {
                if (winner.equals(Role.BLACK.team, ignoreCase = true)) {
                    this.firstDeadLoss++
                }
            }
            this.recalculateCI(numberOfGames)
            when (
                getCurrentTable(table)
                    .filter { it.currentRole.team == "BLACK" }
                    .filter { bestMove.contains(it.currentSlot) }.size
            ) {
                2 -> this.points = (this.points + 0.25).roundUp()
                3 -> this.points = (this.points + 0.4).roundUp()
            }
        }
        getCurrentTable(table).forEach { player ->
            val roleStat = player.stat[player.currentRole.name.toLowerCase()]
            val newTotal = roleStat?.second?.inc()
            var newWin = roleStat?.first
            if (player.currentRole.team.equals(winner, ignoreCase = true)) {
                player.points = (player.points + 1).roundUp()
                newWin = roleStat?.first?.inc()
            }
            player.stat[player.currentRole.name.toLowerCase()] = Pair(newWin ?: 0, newTotal ?: 0)
            player.reset()
        }
        sendGameInfo()
        gameInfoHistory.add(gameInfo)
        gameInfo = GameInfo()
        currentGame++
    }

    fun updateGameInfo() {
        gameInfo.players.clear()
        gameInfo.players.addAll(players.values.sortedBy { it.currentSlot }.map { it.toPlayerInfo() })
    }

    fun sendGameInfo() {
        updateGameInfo()
        val gson = Gson()

        val gameInfoString = gson.toJson(gameInfo)

        runBlocking {
            launch {
                Application.send("overlay", "!gameinfo $gameInfoString")
            }
        }
    }

}