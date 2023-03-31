package foundry.animamundi.grimoire;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import foundry.animamundi.AnimaMundi;
import foundry.animamundi.content.block.alembic.AlembicBlockEntity;
import foundry.animamundi.content.block.alembic.AlembicBlockEntityRenderer;


public class AnimaMundiBlockEntities {
    private static Registrate REGISTRATE = AnimaMundiItems.INSTANCE.getRegister();
    public static void init() {
        // Register Block Entities
    }
    public static BlockEntityEntry<AlembicBlockEntity> ALEMBIC = REGISTRATE.blockEntity("alembic", AlembicBlockEntity::new)
            .validBlock(AnimaMundiBlocks.ALEMBIC)
            .renderer(() -> AlembicBlockEntityRenderer::new)
            .register();
}