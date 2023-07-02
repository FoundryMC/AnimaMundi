package foundry.animamundi.content.brain.cortex.cortices

import foundry.animamundi.content.brain.cortex.Cortex
import foundry.animamundi.content.brain.signal.EmotionSignal
import foundry.animamundi.content.brain.signal.NeuralSignal
import foundry.animamundi.content.brain.signal.NumericalSignal
import foundry.animamundi.content.brain.signal.VectorSignal

class CombinationCortex: Cortex() {
    lateinit var a: InputPort
    lateinit var b: InputPort
    lateinit var outEmotion: NeuralSignal.Type
    lateinit var outNumber: NeuralSignal.Type
    lateinit var outVector: NeuralSignal.Type

    override fun registerDefaults() {
        a = input("a", NeuralSignal.Type.EMOTION, NeuralSignal.Type.NUMERICAL, NeuralSignal.Type.VECTOR)
        b = input("b", NeuralSignal.Type.EMOTION, NeuralSignal.Type.NUMERICAL, NeuralSignal.Type.VECTOR)
        outEmotion = output(NeuralSignal.Type.EMOTION)
        outNumber = output(NeuralSignal.Type.NUMERICAL)
        outVector = output(NeuralSignal.Type.VECTOR)
    }

    override fun process() {
        val a = a()
        val b = b()

        if (a is EmotionSignal && b is EmotionSignal) {
            outEmotion(a + b)
        }

        if (a is NumericalSignal && b is NumericalSignal) {
            outNumber(a + b)
        }

        if (a is VectorSignal && b is VectorSignal) {
            outVector(a + b)
        }
    }
}