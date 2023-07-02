package foundry.animamundi.grimoire;


import foundry.animamundi.AnimaMundi;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class AnimaMundiTags {
    public static final TagKey<Block> LIVING = tag("living");

    private static TagKey<Block> tag(String path) {
        return BlockTags.create(AnimaMundi.Companion.path(path));
    }
}