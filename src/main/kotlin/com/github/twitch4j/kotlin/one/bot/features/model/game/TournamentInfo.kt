package com.github.twitch4j.kotlin.one.bot.features.model.game

import kotlin.math.roundToInt

object TournamentInfo {
    var numberOfGames = 10
    var currentGame = 1

    class Player(val name: String, val team: String = "") {
        var currentTable: Int = 0
        var currentSlot: Int = 0
        var currentRole: Role = Role.RED
        var points: Double = 0.0
        var firstDead: Int = 0
        var firstDeadLoss: Int = 0
        var ciPoints: Double = 0.0

        fun recalculateCI(gamesCount: Int) {
            val b = gamesCount.times(0.4).roundToInt()
            val ci = if (firstDead <= b) firstDead * 0.4 / b else 0.4
            ciPoints = ci * firstDeadLoss
        }

        fun reset() {
            currentTable = 0
            currentSlot = 0
            currentRole = Role.RED
        }
    }

    val players: MutableMap<String, Player> = mutableMapOf()

    fun resetRoles() {
        players.values.forEach { it.currentRole = Role.RED }
    }

    fun getCurrentTable(tableNumber: Int = 0): Array<Player> {
        return players.values.filter { it.currentTable == tableNumber }.sortedBy { it.currentSlot }.toTypedArray()
    }

    fun getResults(): List<Player> {
        return players.values.sortedByDescending { it.points + it.ciPoints }
    }

    fun getResultLines(): List<String> {
        return getResults().withIndex()
            .map { "${it.index + 1}. ${it.value.name} - ${it.value.points + it.value.ciPoints}" }
    }

    fun getActiveRoles(tableNumber: Int = 0): List<Player> {
        return getCurrentTable(tableNumber).filter { it.currentRole != Role.RED }
    }

    fun resolveRound(winner: String, firstDead: Int, bestMove: List<Int>, table: Int = 0) {
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
                2 -> this.points += 0.25
                3 -> this.points += 0.4
            }
        }
        getCurrentTable(table).forEach { player ->
            if (player.currentRole.team.equals(winner, ignoreCase = true)) {
                player.points++
            }
            player.reset()
        }
        currentGame++
    }

}