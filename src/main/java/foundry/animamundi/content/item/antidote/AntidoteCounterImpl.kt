package foundry.animamundi.content.item.antidote

import net.minecraft.nbt.CompoundTag

class AntidoteCounterImpl: AntidoteCounter {
    private var count: Int = 0
    private var ticks: Int = 0
    override fun getAntidoteCount(): Int {
        return count
    }

    override fun setAntidoteCount(count: Int) {
        this.count = count
    }

    override fun addAntidoteCount(count: Int) {
        this.count += count
    }

    override fun removeAntidoteCount(count: Int) {
        this.count -= count
    }

    override fun getTicks(): Int {
        return ticks
    }

    override fun setTicks(ticks: Int) {
        this.ticks = ticks
    }

    override fun addTicks(ticks: Int) {
        this.ticks += ticks
    }

    override fun removeTicks(ticks: Int) {
        this.ticks -= ticks
    }

    override fun tick() {
        if(ticks > 0 && count > 0) ticks--
        if(ticks == 0 && count > 0) count--
    }

    override fun toNBT(): CompoundTag {
        val nbt = CompoundTag()
        nbt.putInt("count", count)
        nbt.putInt("ticks", ticks)
        return nbt
    }

    override fun fromNBT(nbt: CompoundTag) {
        count = nbt.getInt("count")
        ticks = nbt.getInt("ticks")
    }

    companion object {
        fun fromNBT(nbt: CompoundTag): AntidoteCounter {
            val ant = AntidoteCounterImpl()
            ant.fromNBT(nbt)
            return ant
        }
    }
}