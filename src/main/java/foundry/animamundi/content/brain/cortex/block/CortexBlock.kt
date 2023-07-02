package foundry.animamundi.content.brain.cortex.block

import foundry.animamundi.content.brain.cortex.CortexEntry
import foundry.animamundi.grimoire.AnimaMundiBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class CortexBlock(pProperties: Properties, val cortexEntry: CortexEntry<*>) : EntityBlock, Block(pProperties) {
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return CortexBlockEntity(AnimaMundiBlockEntities.CORTEX.get(), pPos, pState)
    }

    override fun <T : BlockEntity?> getTicker(
        pLevel: Level,
        pState: BlockState,
        pBlockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { pLevel, pPos, pState, pBlockEntity ->
            if(pBlockEntity is CortexBlockEntity) {
                pBlockEntity.tick(pLevel, pPos, pState, pBlockEntity)
            }
        }
    }
}