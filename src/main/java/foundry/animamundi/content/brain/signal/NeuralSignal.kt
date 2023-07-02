package foundry.animamundi.content.brain.signal

/**
 * A signal transmitted between cortices in brains
 */
abstract class NeuralSignal<out T>(val type: Type) {

    fun damp(scalar: Double) = mul(scalar)
    fun spike(scalar: Double) = mul(scalar)
    abstract fun mul(scalar: Double)

    fun self(): T {
        return this as T
    }

    enum class Type {
        /**
         * A simple, raw numerical signal
         */
        NUMERICAL,

        /**
         * A simple, raw vector signal
         */
        VECTOR,

        /**
         * A signal representing an emotion mix
         */
        EMOTION,

        /**
         * A signal representing a visual observation of the world
         */
        VISION,

        /**
         * A signal representing a sound observation of the world
         */
        SOUND,

        /**
         * A signal representing a smell observation of the world
         */
        SMELL,

        /**
         * A signal representing touching an entity or the world
         */
        TOUCH,

        /**
         * A signal representing pain
         */
        PAIN,

        /**
         * A signal representing desired bodily movement
         */
        BODY_WHOLE_MOTOR;
    }
}