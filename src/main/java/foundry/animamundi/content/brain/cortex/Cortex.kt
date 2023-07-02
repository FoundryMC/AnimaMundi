package foundry.animamundi.content.brain.cortex

import foundry.animamundi.content.brain.signal.EmotionSignal
import foundry.animamundi.content.brain.signal.NeuralSignal
import foundry.animamundi.content.brain.signal.NumericalSignal
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import kotlin.reflect.KProperty

/**
 * A cortex, or singular "node" in the brain.
 */
abstract class Cortex {

    /**
     * Delegate getters
     */
    val doubleIn: InputDelegate<Double>
        get() = InputDelegate(NeuralSignal.Type.NUMERICAL) { (it as NumericalSignal).value }

    val emotionIn: InputDelegate<EmotionSignal>
        get() = InputDelegate(NeuralSignal.Type.EMOTION) { it as EmotionSignal }

    val doubleOut: OutputDelegate<Double>
        get() = OutputDelegate(NeuralSignal.Type.NUMERICAL) { NumericalSignal(it!!) }

    val emotionOut: OutputDelegate<EmotionSignal>
        get() = OutputDelegate(NeuralSignal.Type.EMOTION) { it as EmotionSignal }

    /**
     * The inputs, or parameters of this cortex.
     * These can technically? be dynamic, but the system is not designed around it.
     */
    var inputs = linkedMapOf<String, InputPort>()

    /**
     * Input dependencies of the input ports.
     * Can be thought of as the origin of the nerve leading to the port.
     */
    var inputDependencies = linkedMapOf<String, Dependency>()

    /**
     * The outputs of this cortex.
     * The cortex can only have one output of each type.
     */
    var outputs = mutableListOf<NeuralSignal.Type>()

    /**
     * The cached output signals of this cortex after processing.
     */
    var outputSignals = mutableMapOf<NeuralSignal.Type, NeuralSignal<*>>()

    /**
     * The cached input signals of this cortex after processing.
     */
    var inputSignals = mutableMapOf<InputPort, NeuralSignal<*>>()

    /**
     * Dummy function used simply to preload delegates on register.
     * Delegates are a little silly like that
     */
    fun dummy(vararg vals: Any?) {}

    /**
     * Extension to get the signal of an input port
     */
    operator fun InputPort.invoke(): NeuralSignal<*> {
        return inputSignals[this]!!
    }

    /**
     * Extension to set the signal of an output port
     */
    operator fun NeuralSignal.Type.invoke(signal: NeuralSignal<*>) {
        outputSignals[this] = signal
    }

    /**
     * Modifies a signal travelling through this cortex.
     * By default, dampens a tiny bit.
     */
    fun modifyPassthrough(signal: NeuralSignal<*>) {
        signal.damp(0.95)
    }

    /**
     * Adds an input port to this cortex
     */
    fun input(name: String, vararg types: NeuralSignal.Type): InputPort {
        val port = InputPort(types.toSet())
        inputs[name] = port
        return port;
    }

    /**
     * Adds an output port to this cortex
     */
    fun output(type: NeuralSignal.Type): NeuralSignal.Type {
        outputs.add(type)
        return type
    }

    /**
     * Registers all input/output ports
     */
    abstract fun registerDefaults()

    /**
     * Executes the processing of this cortex
     */
    abstract fun process()

    /**
     * A dependency on an output of another cortex
     */
    data class Dependency(val other: BlockPos, val type: NeuralSignal.Type) {
        fun serialize(): CompoundTag {
            val tag = CompoundTag()
            tag.putLong("pos", other.asLong())
            tag.putInt("type", type.ordinal)
            return tag
        }

        companion object {
            fun deserialize(tag: CompoundTag): Dependency {
                val pos = BlockPos.of(tag.getLong("pos"))
                val type = NeuralSignal.Type.values()[tag.getInt("type")]
                return Dependency(pos, type)
            }
        }
    }

    /**
     * An input port for a cortex
     * @param types The types of signals that can be received by this port
     */
    data class InputPort(val types: Set<NeuralSignal.Type>) {
        constructor(vararg types: NeuralSignal.Type): this(types.toSet())

        fun allowed(signal: NeuralSignal<*>) = signal.type in types
        fun allowed(type: NeuralSignal.Type) = type in types
    }

    /**
     * An input delegate
     */
    class InputDelegate<T>(val type: NeuralSignal.Type, val processor: (NeuralSignal<*>) -> T) {

        var input: InputPort? = null

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T {

            if (thisRef is Cortex) {
                if (input == null)
                    input = thisRef.input(property.name, type)

                val signal = thisRef.inputSignals[input]!!
                return processor(signal);
            }

            throw IllegalStateException("Input delegate must be used in a cortex")
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {

        }
    }

    /**
     * An output delegate
     */
    class OutputDelegate<T>(val type: NeuralSignal.Type, val processor: (T?) -> NeuralSignal<*>) {

        var output: NeuralSignal.Type? = null

        operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
            return null
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
            if (thisRef is Cortex) {
                if (output == null)
                    output = thisRef.output(type)

                val signal = processor(value)
                thisRef.outputSignals[output!!] = signal
            }

            throw IllegalStateException("Output delegate must be used in a cortex")
        }
    }
}