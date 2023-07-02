package foundry.animamundi.content.brain.cortex.cortices

import foundry.animamundi.content.brain.cortex.Cortex
import foundry.animamundi.content.brain.signal.EmotionSignal

class MotorCortex: Cortex() {
    var speed by doubleIn

    override fun registerDefaults() {
        dummy(speed)
    }

    override fun process() {

    }
}