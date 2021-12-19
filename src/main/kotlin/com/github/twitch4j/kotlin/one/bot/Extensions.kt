package com.github.twitch4j.kotlin.one.bot

fun <T> Iterable<T>.toTwitchList(): String {
    return "⠤".repeat(30).plus(this.map { it.toString() }.reduce { prev, next ->
        "$prev ${"⠤".repeat(30)} $next"
    })
}