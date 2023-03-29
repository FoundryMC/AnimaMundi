package foundry.animamundi.content.item.antidote

import net.minecraft.core.Direction
import net.minecraft.nbt.CompoundTag
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.CapabilityToken
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import net.minecraftforge.common.util.LazyOptional

class AntidoteCounterProvider: ICapabilitySerializable<CompoundTag> {
    private var CAP: AntidoteCounter = AntidoteCounterImpl()
    override fun <T : Any?> getCapability(cap: Capability<T>, side: Direction?): LazyOptional<T> {
        return if(cap == AntidoteCounterHandler.CAPABILITY) {
            LazyOptional.of { CAP }.cast()
        } else {
            LazyOptional.empty()
        }
    }

    override fun serializeNBT(): CompoundTag {
        return CAP.toNBT()
    }

    override fun deserializeNBT(nbt: CompoundTag) {
        CAP.fromNBT(nbt)
    }
}