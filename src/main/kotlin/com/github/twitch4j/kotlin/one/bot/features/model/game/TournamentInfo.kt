package com.github.twitch4j.kotlin.one.bot.features.model.game

object TournamentInfo {
    var numberOfGames = 10
    var currentGame = 1

    class Player(val name: String, val team: String = "") {
        var currentTable: Int = 0
        var currentSlot: Int = 0
        var currentRole: Role = Role.RED
        var points: Double = 0.0

        fun reset() {
            currentTable = 0
            currentSlot = 0
            currentRole = Role.RED
        }
    }

    val players: MutableMap<String, Player> = mutableMapOf()

    fun getCurrentTable(tableNumber: Int = 0): Array<Player> {
        return players.values.filter { it.currentTable == tableNumber }.sortedBy { it.currentSlot }.toTypedArray()
    }

    fun getResults(): List<Player> {
        return players.values.sortedBy { it.points }
    }

    fun getActiveRoles(tableNumber: Int = 0): List<Player> {
        return getCurrentTable(tableNumber).filter { it.currentRole != Role.RED }
    }

    fun resolveRound(winner: String, firstDead: Int, bestMove: List<Int>, table: Int = 0) {
        with(getCurrentTable(table)[firstDead]) {
            when (getCurrentTable(table)
                .filter { it.currentRole.team == "BLACK" }
                .filter { bestMove.contains(it.currentSlot) }.size) {
                2 -> this.points += 0.25
                3 -> this.points += 0.4
            }
        }
        getCurrentTable(table).forEach { player ->
            if (player.currentRole.team == winner) {
                player.points++
            }
            player.reset()
        }
    }

}