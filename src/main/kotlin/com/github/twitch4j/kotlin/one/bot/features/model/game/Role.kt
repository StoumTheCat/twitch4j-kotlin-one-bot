package com.github.twitch4j.kotlin.one.bot.features.model.game

enum class Role(val team: String, val localized: String) {
    RED("RED", "Мирный"), BLACK("BLACK", "Мафия"), HAT("BLACK", "Дон"), STAR("RED", "Шериф")
}