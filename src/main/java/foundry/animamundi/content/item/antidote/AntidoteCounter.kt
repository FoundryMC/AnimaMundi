package foundry.animamundi.content.item.antidote

import net.minecraft.nbt.CompoundTag

interface AntidoteCounter {
    fun getAntidoteCount(): Int
    fun setAntidoteCount(count: Int)
    fun addAntidoteCount(count: Int)
    fun removeAntidoteCount(count: Int)
    fun getTicks(): Int
    fun setTicks(ticks: Int)
    fun addTicks(ticks: Int)
    fun removeTicks(ticks: Int)
    fun tick()
    fun toNBT(): CompoundTag
    fun fromNBT(nbt: CompoundTag)
}