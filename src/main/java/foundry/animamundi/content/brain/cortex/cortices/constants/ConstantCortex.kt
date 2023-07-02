package foundry.animamundi.content.brain.cortex.cortices.constants

import foundry.animamundi.content.brain.cortex.Cortex
import foundry.animamundi.content.brain.signal.NeuralSignal

abstract class ConstantCortex(val type: NeuralSignal.Type, val supplier: () -> NeuralSignal<*>): Cortex() {
    lateinit var out: NeuralSignal.Type

    override fun registerDefaults() {
        out = output(type)
    }

    override fun process() {
        out(supplier())
    }
}