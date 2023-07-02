package foundry.animamundi.grimoire;

import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class AnimaMundiMaterials {
    public static final Material FLESH = (new Material.Builder(MaterialColor.COLOR_RED)).noCollider().nonSolid().build();
    public static final Material BRAIN = (new Material.Builder(MaterialColor.COLOR_LIGHT_GRAY)).noCollider().nonSolid().build();
}
