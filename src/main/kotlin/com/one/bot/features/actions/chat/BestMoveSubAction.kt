package com.one.bot.features.actions.chat

import com.github.twitch4j.chat.events.channel.ChannelMessageEvent
import com.one.bot.Twitch
import com.one.bot.Twitch.twitchChat
import com.one.bot.extractBestMove
import com.one.bot.features.model.CommandAction
import com.one.bot.features.model.chat.BestMoveContest
import com.one.bot.features.model.permissions.PermissionLevel
import com.one.bot.messageWithoutCommand

object BestMoveSubAction : CommandAction(
    command = "bm",
    description = "Accepts best move submission",
    permissions = listOf(PermissionLevel.EVERYONE),
    executable = fun(event: ChannelMessageEvent) {
        if(BestMoveContest.contestRunning) {
            BestMoveContest.submitBestMove(event.user.name, event.messageWithoutCommand.extractBestMove)
        } else {
            twitchChat.sendMessage(event.channel.name, "Конкурс уже кончился (или еще не начался)")
        }
    }) {
    init {
        aliases.addAll(listOf("лх"))
    }
}
/*

123
120
1210
1 2 3
1, 2, 10
 */