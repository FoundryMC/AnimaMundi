package foundry.animamundi.content.brain.cortex.cortices.redstone

import foundry.animamundi.content.brain.cortex.Cortex
import foundry.animamundi.content.brain.signal.EmotionSignal

class RedstoneCortex: Cortex() {
    var powerIn by doubleIn
    var powerOut by doubleOut

    var powerFromBE = 0.0
    var powerToBE = 0.0

    override fun registerDefaults() {
        dummy(powerIn, powerOut)
    }

    override fun process() {
        powerOut = powerFromBE
        powerToBE = powerIn
    }
}