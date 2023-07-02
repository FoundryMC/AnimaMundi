package foundry.animamundi.content.brain.cortex.block

import foundry.animamundi.content.brain.cortex.Cortex
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState

class CortexBlockEntity(pType: BlockEntityType<*>, pPos: BlockPos, pBlockState: BlockState) : BlockEntity(pType, pPos, pBlockState) {
    var cortex: Cortex? = null
    var clockTicked = false

    private fun makeCortexIfNone() {
        if (cortex == null) {
            val block = blockState.block
            if (block is CortexBlock) {
                cortex = block.cortexEntry.newCortex()
            }
        }
    }

    fun tick(level: Level, pos: BlockPos, state: BlockState, be: CortexBlockEntity) {
        if (level.isClientSide) return;

        makeCortexIfNone()

        if (!clockTicked) {

        }

        clockTicked = false
    }

    override fun saveAdditional(pTag: CompoundTag) {
        super.saveAdditional(pTag)

        if (!level!!.isClientSide) {
            val dependencyTag = CompoundTag()

            val dependencies = cortex!!.inputDependencies

            dependencies.forEach { (t, u) ->
                dependencyTag.put(t, u.serialize())
            }

            pTag.put("dependencies", dependencyTag)
        }
    }

    override fun load(pTag: CompoundTag) {
        super.load(pTag)

        if (!level!!.isClientSide) {
            makeCortexIfNone()
            val dependencyTag = pTag.getCompound("dependencies")

            dependencyTag.allKeys.forEach {
                val tag = dependencyTag.getCompound(it)

                val dependency = Cortex.Dependency.deserialize(tag)
                cortex!!.inputDependencies[it] = dependency
            }
        }
    }
}