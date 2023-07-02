package foundry.animamundi.content.brain.cortex

class CortexEntry<T: Cortex>(private val supplier: () -> T) {

    /**
     * Constructs a new programming block of this type
     */
    fun newCortex(): T {
        return supplier()
    }

}