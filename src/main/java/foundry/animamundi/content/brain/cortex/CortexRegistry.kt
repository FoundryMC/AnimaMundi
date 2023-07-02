package foundry.animamundi.content.brain.cortex

import foundry.animamundi.AnimaMundi
import foundry.animamundi.content.brain.cortex.cortices.CombinationCortex
import foundry.animamundi.content.brain.cortex.cortices.MotorCortex
import foundry.animamundi.content.brain.cortex.cortices.constants.*
import net.minecraft.resources.ResourceLocation

object CortexRegistry {
    private var forwardRegistry: MutableMap<ResourceLocation, CortexEntry<*>> =
        LinkedHashMap<ResourceLocation, CortexEntry<*>>()
    private var inverseRegistry: MutableMap<CortexEntry<*>, ResourceLocation> =
        LinkedHashMap<CortexEntry<*>, ResourceLocation>()

    fun <T: Cortex> register(id: ResourceLocation, entry: () -> T): CortexEntry<T> {
        val cortexEntry: CortexEntry<T> = CortexEntry(entry)
        forwardRegistry[id] = cortexEntry
        inverseRegistry[cortexEntry] = id

        return cortexEntry
    }

    fun <T: Cortex> simple(id: String, entry: () -> T) = register(AnimaMundi.path(id), entry)

    fun <T: Cortex> get(id: ResourceLocation): CortexEntry<T>? {
        return forwardRegistry[id] as CortexEntry<T>?
    }

    fun <T: Cortex> get(entry: CortexEntry<T>): ResourceLocation? {
        return inverseRegistry[entry]
    }

    val positive = simple("positive") { PositiveCortex() }
    val rage = simple("rage") { RageCortex() }
    val dormant = simple("dormant") { DormantCortex() }
    val panic = simple("panic") { PanicCortex() }
    val motor = simple("motor") { MotorCortex() }
    val combination = simple("combination") { CombinationCortex() }

}