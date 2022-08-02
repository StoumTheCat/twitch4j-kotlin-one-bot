package com.one.bot.obs

import net.twasi.obsremotejava.OBSRemoteController
import net.twasi.obsremotejava.requests.SetCurrentScene.SetCurrentSceneResponse

object OBSHelper {
    val controller = OBSRemoteController("ws://localhost:4444", false)

    fun switchDayNight() {
        controller.getCurrentScene {
            if (it.name == "Night") {
                controller.setCurrentScene("Day") {}
            } else {
                controller.setCurrentScene("Night") {}
            }
        }
    }

    fun updateBrowserSourceUrl(youtubeId: String) {
        controller.setSourceSettings(
            "Browser",
            mapOf(Pair("url", "https://www.youtube.com/embed/${youtubeId}?autoplay=1"))
        ) {
            println(it)
        }
    }

    fun setSceneSolo() {
        controller.setCurrentScene("Solo") {}
    }

    fun setSceneOneGuest() {
        controller.setCurrentScene("With 1 Guest") {}
    }

    fun setSceneTwoGuests() {
        controller.setCurrentScene("With 2 Guests") {}
    }

    fun setSceneResults() {
        controller.setCurrentScene("Results") {}
    }

    fun setScene(scene: String, callback: (SetCurrentSceneResponse) -> Unit) {
        controller.setCurrentScene(scene, callback)
    }
}