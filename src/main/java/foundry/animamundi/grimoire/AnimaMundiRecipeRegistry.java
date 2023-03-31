package foundry.animamundi.grimoire;

import foundry.animamundi.AnimaMundi;
import foundry.animamundi.content.block.mortar.MortarRecipe;
import foundry.animamundi.content.block.mortar.MortarRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AnimaMundiRecipeRegistry {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "animamundi");
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, "animamundi");

    public static RegistryObject<RecipeType<MortarRecipe>> MORTAR_RECIPE = RECIPE_TYPES.register("mortar", () -> RecipeType.simple(AnimaMundi.path("grinding")));
    public static RegistryObject<MortarRecipeSerializer> MORTAR_SERIALIZER = RECIPE_SERIALIZERS.register("mortar", () -> new MortarRecipeSerializer(MORTAR_RECIPE.get()));
}
