package foundry.animamundi.content.brain.signal

import foundry.animamundi.extensions.plus
import foundry.animamundi.extensions.times
import net.minecraft.world.phys.Vec3

class VectorSignal(var value: Vec3): NeuralSignal<VectorSignal>(Type.VECTOR) {

    override fun mul(scalar: Double) {
        value *= scalar
    }

    operator fun plus(b: VectorSignal): NeuralSignal<*> {
        return VectorSignal(value + b.value)
    }

    override fun toString(): String {
        return "VectorSignal(value=$value)"
    }
}