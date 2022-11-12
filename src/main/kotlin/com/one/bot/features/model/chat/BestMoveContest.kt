package com.one.bot.features.model.chat

object BestMoveContest {
    val participants : MutableMap<String, Double> = mutableMapOf()
    val currentRound : MutableMap<String, IntArray> = mutableMapOf()

    private fun validateBm(bm : IntArray) : IntArray {
        return bm.distinct().toIntArray()
    }

    fun submitBestMove(userName: String, bm : IntArray) : Boolean {
        if (currentRound.containsKey(userName)) return false
        currentRound[userName] = validateBm(bm)
        return true
    }

    fun resolve(bm : IntArray) : List<String> {
        var winners : MutableList<String> = mutableListOf()
        currentRound.entries.forEach { userRound ->
            when (userRound.value.count { bm.contains(it) }) {
                2 -> {
                    winners.add("${userRound.key} - 0.25")
                    participants[userRound.key] = participants[userRound.key]?.plus(0.25) ?: 0.25
                }
                3 -> {
                    winners.add("${userRound.key} - 0.5")
                    participants[userRound.key] = participants[userRound.key]?.plus(0.5) ?: 0.5
                }
            }
        }

        currentRound.clear()
        return winners
    }

    @Volatile
    var contestRunning : Boolean = false
}