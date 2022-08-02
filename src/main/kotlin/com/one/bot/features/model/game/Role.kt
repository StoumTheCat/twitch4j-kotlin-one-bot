package com.one.bot.features.model.game

enum class Role(val team: String, val localized: String) {
    RED("RED", "Мирный"), BLACK("BLACK", "Мафия"), DON("BLACK", "Дон"), SHER("RED", "Шериф")
}