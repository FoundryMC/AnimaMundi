package foundry.animamundi.grimoire

import com.tterrag.registrate.util.entry.ItemEntry
import foundry.animamundi.AnimaMundi
import foundry.animamundi.content.item.AnimaMundiItemMaterials
import foundry.animamundi.content.item.grapple.SimpleGrapplingHook
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.ShieldItem
import net.minecraft.world.item.SwordItem

object AnimaMundiItems {

    private val REGISTRATE = AnimaMundi.registrate()
        .creativeModeTab { AnimaMundiItemGroups.MAIN_GROUP }

    fun register() {

    }

    val SIMPLE_GRAPPLING_HOOK: ItemEntry<SimpleGrapplingHook> = REGISTRATE.item<SimpleGrapplingHook>("simple_grappling_hook") { props: Properties -> SimpleGrapplingHook(props) }.register()
    val SILVER_INGOT: ItemEntry<Item> = REGISTRATE.item<Item>("silver_ingot") { props: Properties ->  Item(props) }.register()

    val SILVER_HELMET: ItemEntry<Item> = REGISTRATE.item<Item>("silver_helmet") { props: Properties ->  ArmorItem(AnimaMundiItemMaterials.SILVER_ARMOR, EquipmentSlot.HEAD, props) }.register()
    val SILVER_CHESTPLATE: ItemEntry<Item> = REGISTRATE.item<Item>("silver_chestplate") { props: Properties ->  ArmorItem(AnimaMundiItemMaterials.SILVER_ARMOR, EquipmentSlot.CHEST, props) }.register()
    val SILVER_LEGGINGS: ItemEntry<Item> = REGISTRATE.item<Item>("silver_leggings") { props: Properties ->  ArmorItem(AnimaMundiItemMaterials.SILVER_ARMOR, EquipmentSlot.LEGS, props) }.register()
    val SILVER_BOOTS: ItemEntry<Item> = REGISTRATE.item<Item>("silver_boots") { props: Properties ->  ArmorItem(AnimaMundiItemMaterials.SILVER_ARMOR, EquipmentSlot.FEET, props) }.register()
    val SILVER_SWORD: ItemEntry<Item> = REGISTRATE.item<Item>("silver_sword") { props: Properties ->  SwordItem(AnimaMundiItemMaterials.SILVER_TOOLS, 3, -2.4f, props) }.register()
    val WAILING_SHIELD: ItemEntry<Item> = REGISTRATE.item<Item>("wailing_shield") { props: Properties -> ShieldItem(props) }.register()

}