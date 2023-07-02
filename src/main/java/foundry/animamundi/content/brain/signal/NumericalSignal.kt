package foundry.animamundi.content.brain.signal

class NumericalSignal(var value: Double): NeuralSignal<NumericalSignal>(Type.NUMERICAL) {

    override fun mul(scalar: Double) {
        value *= scalar;
    }

    operator fun plus(b: NumericalSignal): NeuralSignal<*> {
        return NumericalSignal(value + b.value)
    }

    override fun toString(): String {
        return "NumericalSignal(value=$value)"
    }
}