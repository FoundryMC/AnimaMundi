package foundry.animamundi.content.item

import net.minecraft.world.item.crafting.Ingredient
import foundry.animamundi.grimoire.AnimaMundiItems;
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.Tier

class AnimaMundiItemMaterials{
    companion object{
        val SILVER_ARMOR: ArmorMaterial = AnimaMundiArmorMaterial("silver", 15, intArrayOf(2, 5, 6, 2), 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0f, 0.0f, Ingredient.of(AnimaMundiItems.SILVER_INGOT.get()))
        val SILVER_TOOLS: Tier = AnimaMundiItemTier(2, 200, 5.0f, 1.5f, 18, Ingredient.of(AnimaMundiItems.SILVER_INGOT.get()))
        val ANTIMONY_TOOLS: Tier = AnimaMundiItemTier(2, 200, 5.0f, 1.5f, 18, Ingredient.of(AnimaMundiItems.ANTIMONY_INGOT.get()))
    }
}