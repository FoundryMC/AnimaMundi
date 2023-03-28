package foundry.animamundi.grimoire;

import com.tterrag.registrate.util.entry.EntityEntry;
import foundry.animamundi.AnimaMundi;
import foundry.animamundi.content.entity.hook.HookEntity;
import foundry.animamundi.content.entity.hook.HookEntityRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class AnimaMundiEntities {

    public static void register() {}

    public static final EntityEntry<HookEntity> HOOK = AnimaMundi.registrate().entity("hook", HookEntity::new, MobCategory.MISC)
            .properties(b -> b.setTrackingRange(5)
                    .setUpdateInterval(1)
                    .setShouldReceiveVelocityUpdates(false))
            .properties((p) -> p.sized(1.0F, 1.0F))
            .properties(EntityType.Builder::fireImmune)
            .renderer(() -> HookEntityRenderer::new)
            .register();

}
