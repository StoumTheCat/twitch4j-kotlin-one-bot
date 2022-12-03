package com.one.bot.obs

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import io.obswebsocket.community.client.OBSRemoteController

object OBSHelper {
    val controller = OBSRemoteController.builder()
        .host("localhost")                  // Default host
        .port(4455)                         // Default port
        .connectionTimeout(3)               // Seconds the client will wait for OBS to respond
        .build();

    init {
        controller.connect()
    }

    fun switchDayNight() {
        controller.getCurrentPreviewScene {
            if (it.currentPreviewSceneName == "Night") {
                controller.setCurrentProgramScene("Day") {}
            } else {
                controller.setCurrentProgramScene("Night") {}
            }
        }
    }

    fun updateBrowserSourceUrl(youtubeId: String) {
        val settings = JsonObject()

        settings.add("url", JsonPrimitive("https://www.youtube.com/embed/${youtubeId}?autoplay=1"))
        controller.setInputSettings(
            "GameSource",
            settings,
            true
        ) {
            println(it)
        }
    }

    fun setSceneSolo() {
        controller.setCurrentProgramScene("Solo") {}
    }

    fun setSceneOneGuest() {
        controller.setCurrentProgramScene("With 1 Guest") {}
    }

    fun setSceneTwoGuests() {
        controller.setCurrentProgramScene("With 2 Guests") {}
    }

    fun setSceneResults() {
        controller.setCurrentProgramScene("Results") {}
    }

    /*fun setScene(scene: String, callback: (SetCurrentSceneResponse) -> Unit) {
        controller.setCurrentScene(scene, callback)
    }*/
}