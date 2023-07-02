package foundry.animamundi.content.brain.signal

import foundry.animamundi.extensions.times
import net.minecraft.world.phys.Vec3

class EmotionSignal(var happiness: Double, var anger: Double, var panic: Double, var sleepiness: Double): NeuralSignal<EmotionSignal>(Type.EMOTION) {

    override fun mul(scalar: Double) {
        happiness *= scalar
        anger *= scalar
        panic *= scalar
        sleepiness *= scalar
    }

    operator fun plus(b: EmotionSignal): NeuralSignal<*> {
        return EmotionSignal(happiness + b.happiness, anger + b.anger, panic + b.panic, sleepiness + b.sleepiness)
    }

    override fun toString(): String {
        return "EmotionSignal(happiness=$happiness, anger=$anger, panic=$panic, sleepiness=$sleepiness)"
    }
}