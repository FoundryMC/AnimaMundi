package foundry.animamundi.grimoire

import com.tterrag.registrate.providers.DataGenContext
import com.tterrag.registrate.providers.RegistrateItemModelProvider
import com.tterrag.registrate.util.entry.ItemEntry
import com.tterrag.registrate.util.nullness.NonNullBiConsumer
import foundry.animamundi.AnimaMundi
import foundry.animamundi.content.item.AnimaMundiItemMaterials
import foundry.animamundi.content.item.antidote.AntidoteItem
import net.minecraft.world.item.*
import net.minecraft.world.item.Item.Properties

object AnimaMundiItems {

    val REGISTRATE = AnimaMundi.registrate()
        .creativeModeTab { AnimaMundiItemGroups.MAIN_GROUP }

    fun getRegister() = REGISTRATE
    fun register() {

    }

    fun <T : Item?> itemModel(name: String): NonNullBiConsumer<DataGenContext<Item?, T>?, RegistrateItemModelProvider>? {
        return NonNullBiConsumer { c: DataGenContext<Item?, T>?, p: RegistrateItemModelProvider ->
            p.getExistingFile(
                p.modLoc("item/$name")
            )
        }
    }

    // Materials
    val SILVER_INGOT: ItemEntry<Item> = REGISTRATE.item<Item>("silver_ingot") { props: Properties ->  Item(props) }.register()
    val SILVER_NUGGET: ItemEntry<Item> = REGISTRATE.item<Item>("silver_nugget") { props: Properties ->  Item(props) }.register()
    val RAW_SILVER: ItemEntry<Item> = REGISTRATE.item<Item>("raw_silver") { props: Properties ->  Item(props) }.register()
    val ARAMID_FABRIC: ItemEntry<Item> = REGISTRATE.item<Item>("aramid_fabric") { props: Properties ->  Item(props) }.register()
    val ANTIMONY_INGOT: ItemEntry<Item> = REGISTRATE.item<Item>("antimony_ingot") { props: Properties ->  Item(props) }.register()
    val ANTIMONY_NUGGET: ItemEntry<Item> = REGISTRATE.item<Item>("antimony_nugget") { props: Properties ->  Item(props) }.register()
    val BONE_ASH: ItemEntry<Item> = REGISTRATE.item<Item>("bone_ash") { props: Properties ->  Item(props) }.register()
    val MIASMANE_INGOT: ItemEntry<Item> = REGISTRATE.item<Item>("miasmane_ingot") { props: Properties ->  Item(props) }.register()
    val PESTLE: ItemEntry<Item> = REGISTRATE.item<Item>("pestle") { props: Properties ->  Item(props) }.register()
    val TOXIN: ItemEntry<Item> = REGISTRATE.item<Item>("toxin") { props: Properties ->  Item(props) }.register()
    val SEDATIVE: ItemEntry<Item> = REGISTRATE.item<Item>("sedative") { props: Properties ->  Item(props) }.register()
    val ANTIDOTE: ItemEntry<Item> = REGISTRATE.item<Item>("antidote") { props: Properties ->  AntidoteItem(props) }.register()

    // Equipment
    // val SIMPLE_GRAPPLING_HOOK: ItemEntry<SimpleGrapplingHook> = REGISTRATE.item<SimpleGrapplingHook>("simple_grappling_hook") { props: Properties -> SimpleGrapplingHook(props) }.register()

    val SILVER_SWORD: ItemEntry<Item> = REGISTRATE.item<Item>("silver_sword") { props: Properties ->  SwordItem(AnimaMundiItemMaterials.SILVER_TOOLS, 3, -2.4f, props) }.register()
    val SILVER_PICKAXE: ItemEntry<Item> = REGISTRATE.item<Item>("silver_pickaxe") { props: Properties ->  PickaxeItem(AnimaMundiItemMaterials.SILVER_TOOLS, 3, -2.4f, props) }.register()
    val SILVER_SHOVEL: ItemEntry<Item> = REGISTRATE.item<Item>("silver_shovel") { props: Properties ->  ShovelItem(AnimaMundiItemMaterials.SILVER_TOOLS, 3f, -2.4f, props) }.register()
    val SILVER_AXE: ItemEntry<Item> = REGISTRATE.item<Item>("silver_axe") { props: Properties ->  AxeItem(AnimaMundiItemMaterials.SILVER_TOOLS, 3f, -2.4f, props) }.register()
    val SILVER_HOE: ItemEntry<Item> = REGISTRATE.item<Item>("silver_hoe") { props: Properties ->  HoeItem(AnimaMundiItemMaterials.SILVER_TOOLS, 3, -2.4f, props) }.register()

    val ANTIMONY_AXE: ItemEntry<Item> = REGISTRATE.item<Item>("antimony_axe") { props: Properties ->  AxeItem(AnimaMundiItemMaterials.ANTIMONY_TOOLS, 3f, -2.4f, props) }.register()
    val ANTIMONY_HOE: ItemEntry<Item> = REGISTRATE.item<Item>("antimony_hoe") { props: Properties ->  HoeItem(AnimaMundiItemMaterials.ANTIMONY_TOOLS, 3, -2.4f, props) }.register()
    val ANTIMONY_PICKAXE: ItemEntry<Item> = REGISTRATE.item<Item>("antimony_pickaxe") { props: Properties ->  PickaxeItem(AnimaMundiItemMaterials.ANTIMONY_TOOLS, 3, -2.4f, props) }.register()
    val ANTIMONY_SHOVEL: ItemEntry<Item> = REGISTRATE.item<Item>("antimony_shovel") { props: Properties ->  ShovelItem(AnimaMundiItemMaterials.ANTIMONY_TOOLS, 3f, -2.4f, props) }.register()
    val ANTIMONY_SWORD: ItemEntry<Item> = REGISTRATE.item<Item>("antimony_sword") { props: Properties ->  SwordItem(AnimaMundiItemMaterials.ANTIMONY_TOOLS, 3, -2.4f, props) }.register()

    val WAILING_SHIELD: ItemEntry<Item> = REGISTRATE.item<Item>("wailing_shield") { props: Properties ->
        ShieldItem(
            props
        )
    }.model(itemModel("wailing_shield")).register()

}