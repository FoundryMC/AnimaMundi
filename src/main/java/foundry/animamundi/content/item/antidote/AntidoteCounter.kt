package foundry.animamundi.content.item.antidote

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.player.Player

interface AntidoteCounter {
    fun getAntidoteCount(): Int
    fun setAntidoteCount(count: Int)
    fun addAntidoteCount(count: Int)
    fun removeAntidoteCount(count: Int)
    fun getTicks(): Int
    fun setTicks(ticks: Int)
    fun addTicks(ticks: Int)
    fun removeTicks(ticks: Int)
    fun tick(player: Player)
    fun toNBT(): CompoundTag
    fun fromNBT(nbt: CompoundTag)
}