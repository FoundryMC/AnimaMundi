package foundry.animamundi.content.item

import net.minecraft.sounds.SoundEvent
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient

open class AnimaMundiArmorMaterial(
    private val name: String, private val durabilityModifier: Int, private val slotProtections: IntArray,
    private val enchantmentValue: Int, private val sound: SoundEvent, private val toughness: Float,
    private val knockbackResistance: Float, private val repairIngredient: Ingredient
): ArmorMaterial {

    override fun getToughness(): Float {
        return toughness
    }

    override fun getKnockbackResistance(): Float {
        return knockbackResistance
    }

    override fun getDurabilityForSlot(pSlot: EquipmentSlot): Int {
        return HEALTH_PER_SLOT[pSlot.index] * durabilityModifier
    }

    override fun getDefenseForSlot(pSlot: EquipmentSlot): Int {
        return slotProtections[pSlot.index]
    }

    override fun getEnchantmentValue(): Int {
        return enchantmentValue
    }

    override fun getEquipSound(): SoundEvent {
        return sound
    }

    override fun getRepairIngredient(): Ingredient {
        return repairIngredient
    }

    override fun getName(): String {
        return name
    }

    companion object{
        val HEALTH_PER_SLOT: IntArray = intArrayOf(13,15,16,11)
    }
}