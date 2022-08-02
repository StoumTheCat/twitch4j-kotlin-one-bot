package com.one.bot.features.model.game

class GameInfo {
    class PlayerInfo(val nickname: String, val role: String?) {
        var status: Pair<String, String>? = null
        var checks: MutableList<Pair<String, String>>? = null
        var stat: MutableMap<String, Pair<String, String>>? = null
    }

    var players: MutableList<PlayerInfo> = mutableListOf()
    var win: String? = null
    var firstKilled: String? = null
    var bestMove: String? = null
}