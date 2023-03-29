package foundry.animamundi.content.item

import net.minecraft.world.item.Tier
import net.minecraft.world.item.crafting.Ingredient

class AnimaMundiItemTier(
    private val harvestLevel: Int,
    private val maxUses: Int,
    private val efficiency: Float,
    private val attackDamage: Float,
    private val enchantability: Int,
    private val repairMaterial: Ingredient
) : Tier {
    override fun getUses(): Int {
        return maxUses
    }

    override fun getSpeed(): Float {
        return efficiency
    }

    override fun getAttackDamageBonus(): Float {
        return attackDamage
    }

    override fun getLevel(): Int {
        return harvestLevel
    }

    override fun getEnchantmentValue(): Int {
        return enchantability
    }

    override fun getRepairIngredient(): Ingredient {
        return repairMaterial
    }
}