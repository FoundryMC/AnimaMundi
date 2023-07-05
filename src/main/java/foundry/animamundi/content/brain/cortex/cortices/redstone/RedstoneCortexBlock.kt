package foundry.animamundi.content.brain.cortex.cortices.redstone

import foundry.animamundi.content.brain.cortex.CortexEntry
import foundry.animamundi.content.brain.cortex.block.CortexBlock
import foundry.animamundi.content.brain.cortex.block.CortexBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.BlockGetter
import net.minecraft.world.level.block.state.BlockState

class RedstoneCortexBlock(props: Properties, cortexEntry: CortexEntry<*>) : CortexBlock(props, cortexEntry) {

    override fun getSignal(pState: BlockState, pLevel: BlockGetter, pPos: BlockPos, pDirection: Direction): Int {
        pLevel.getBlockEntity(pPos)?.let {
            if(it is CortexBlockEntity) {
                val a = (it.cortex as RedstoneCortex)
                return (a.powerToBE * 15.0).toInt()
            }
        }
        return 0
    }

    override fun isSignalSource(pState: BlockState) = true

}