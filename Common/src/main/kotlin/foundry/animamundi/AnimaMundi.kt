package foundry.animamundi

class AnimaMundi {
    companion object {
        const val MOD_ID = "animamundi"
        const val MOD_NAME = "Anima Mundi"
        private const val MOD_VERSION = "1.0.0"

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