package foundry.animamundi

import com.tterrag.registrate.Registrate

class AnimaMundi {
    companion object {
        const val MOD_ID: String = "animamundi"
        var REGISTRATE: Registrate = Registrate.create(MOD_ID)
        const val MOD_NAME: String = "Anima Mundi"
        const val MOD_VERSION = "1.0.0"

        const val MOD_GROUP = "foundry"
        fun init() {
            Constants.LOG.info("Anima Mundi is initializing! " +
                    "Version: $MOD_VERSION" + " Group: $MOD_GROUP" + " ID: $MOD_ID" + " Name: $MOD_NAME")
        }
    }

    init {
        init()
    }

}