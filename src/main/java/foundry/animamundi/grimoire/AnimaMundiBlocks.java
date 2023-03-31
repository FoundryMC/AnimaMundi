package foundry.animamundi.grimoire;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import foundry.animamundi.content.block.alembic.AlembicBlock;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class AnimaMundiBlocks {
    public static void init() {
        // Register Blocks
    }

    public static Registrate REGISTRATE = AnimaMundiItems.INSTANCE.getRegister();


    public static BlockEntry<AlembicBlock> ALEMBIC = REGISTRATE.block("alembic", AlembicBlock::new)
            .initialProperties(Material.METAL)
            .properties(s -> s.noOcclusion().color(MaterialColor.METAL).dynamicShape())
            .simpleItem()
            .register();
}