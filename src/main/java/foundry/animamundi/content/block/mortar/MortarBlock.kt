package foundry.animamundi.content.block.mortar

import foundry.animamundi.grimoire.AnimaMundiBlockEntities
import net.minecraft.core.BlockPos
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.EntityBlock
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult

class MortarBlock(pProperties: Properties) : EntityBlock, Block(pProperties) {
    override fun newBlockEntity(pPos: BlockPos, pState: BlockState): BlockEntity {
        return MortarBlockEntity(AnimaMundiBlockEntities.MORTAR.get(), pPos, pState)
    }

    override fun <T : BlockEntity> getTicker(
        pLevel: Level,
        pState: BlockState,
        pBlockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T> {
        return BlockEntityTicker { level, pPos, state, pBlockEntity ->
            if(pBlockEntity is MortarBlockEntity) {
                pBlockEntity.tick(level, pPos, state, pBlockEntity)
            }
        }
    }

    override fun use(
        pState: BlockState,
        pLevel: Level,
        pPos: BlockPos,
        pPlayer: Player,
        pHand: InteractionHand,
        pHit: BlockHitResult
    ): InteractionResult {
        return pLevel.getBlockEntity(pPos)?.let { pBlockEntity ->
            if(pBlockEntity is MortarBlockEntity) {
                pBlockEntity.use(pState, pLevel, pPos, pPlayer, pHand, pHit)
            } else {
                super.use(pState, pLevel, pPos, pPlayer, pHand, pHit)
            }
        } ?: super.use(pState, pLevel, pPos, pPlayer, pHand, pHit)
    }
}