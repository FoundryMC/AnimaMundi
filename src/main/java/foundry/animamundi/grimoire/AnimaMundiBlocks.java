package foundry.animamundi.grimoire;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import foundry.animamundi.content.block.alembic.AlembicBlock;
import foundry.animamundi.content.block.mortar.MortarBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;

import java.util.function.Function;

public class AnimaMundiBlocks {
    public static void init() {
        // Register Blocks
    }

    public static Registrate REGISTRATE = AnimaMundiItems.INSTANCE.getRegister();

    public static <I extends BlockItem> ItemModelBuilder customItemModel(DataGenContext<Item, I> ctx,
                                                                         RegistrateItemModelProvider prov) {
        return prov.blockItem(() -> ctx.getEntry()
                .getBlock(), "/item");
    }

    public static <I extends BlockItem, P> NonNullFunction<ItemBuilder<I, P>, P> customItemModel() {
        return b -> b.model(AnimaMundiBlocks::customItemModel)
                .build();
    }

    public static <T extends Block> void simpleBlock(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                     Function<BlockState, ModelFile> modelFunc) {
        prov.getVariantBuilder(ctx.getEntry())
                .forAllStatesExcept(state -> {
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .build();
                }, BlockStateProperties.WATERLOGGED);
    }


    private static <T extends Block> void simple(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov) {
        simpleBlock(ctx, prov, blockState -> prov.models().getExistingFile(prov.modLoc("block/" + ctx.getName() + "/block")));
    }

    public static BlockEntry<AlembicBlock> ALEMBIC = REGISTRATE.block("alembic", AlembicBlock::new)
            .initialProperties(Material.METAL)
            .properties(s -> s.noOcclusion().color(MaterialColor.METAL).dynamicShape())
            .blockstate(AnimaMundiBlocks::simple)
            .simpleItem()
            .register();

    public static BlockEntry<MortarBlock> MORTAR = REGISTRATE.block("mortar", MortarBlock::new)
            .initialProperties(Material.METAL)
            .properties(s -> s.noOcclusion().color(MaterialColor.STONE).dynamicShape())
            .blockstate(AnimaMundiBlocks::simple)
            .item().transform(customItemModel())
            .register();
}
