package foundry.animamundi.data

import foundry.animamundi.grimoire.AnimaMundiBlocks
import net.minecraft.core.BlockPos
import net.minecraft.data.DataGenerator
import net.minecraft.util.RandomSource
import net.minecraft.world.level.block.state.BlockState
import net.minecraftforge.client.model.generators.BlockStateProvider
import net.minecraftforge.common.data.ExistingFileHelper

class AnimaMundiBlockStates(gen: DataGenerator?, modid: String?, exFileHelper: ExistingFileHelper?) : BlockStateProvider(gen, modid,
    exFileHelper
) {
    override fun registerStatesAndModels() {
        simpleBlock(AnimaMundiBlocks.ALEMBIC.get())
        simpleBlock(AnimaMundiBlocks.MORTAR.get())
    }

}