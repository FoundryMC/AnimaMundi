package foundry.animamundi.data

import foundry.animamundi.AnimaMundi
import foundry.animamundi.grimoire.AnimaMundiBlocks.ALEMBIC
import foundry.animamundi.grimoire.AnimaMundiBlocks.MORTAR
import foundry.animamundi.grimoire.AnimaMundiItems.ANTIDOTE
import foundry.animamundi.grimoire.AnimaMundiItems.ANTIMONY_AXE
import foundry.animamundi.grimoire.AnimaMundiItems.ANTIMONY_HOE
import foundry.animamundi.grimoire.AnimaMundiItems.ANTIMONY_INGOT
import foundry.animamundi.grimoire.AnimaMundiItems.ANTIMONY_NUGGET
import foundry.animamundi.grimoire.AnimaMundiItems.ANTIMONY_PICKAXE
import foundry.animamundi.grimoire.AnimaMundiItems.ANTIMONY_SHOVEL
import foundry.animamundi.grimoire.AnimaMundiItems.ANTIMONY_SWORD
import foundry.animamundi.grimoire.AnimaMundiItems.ARAMID_FABRIC
import foundry.animamundi.grimoire.AnimaMundiItems.BONE_ASH
import foundry.animamundi.grimoire.AnimaMundiItems.MIASMANE_INGOT
import foundry.animamundi.grimoire.AnimaMundiItems.PESTLE
import foundry.animamundi.grimoire.AnimaMundiItems.RAW_SILVER
import foundry.animamundi.grimoire.AnimaMundiItems.SEDATIVE
import foundry.animamundi.grimoire.AnimaMundiItems.SILVER_AXE
import foundry.animamundi.grimoire.AnimaMundiItems.SILVER_HOE
import foundry.animamundi.grimoire.AnimaMundiItems.SILVER_INGOT
import foundry.animamundi.grimoire.AnimaMundiItems.SILVER_NUGGET
import foundry.animamundi.grimoire.AnimaMundiItems.SILVER_PICKAXE
import foundry.animamundi.grimoire.AnimaMundiItems.SILVER_SHOVEL
import foundry.animamundi.grimoire.AnimaMundiItems.SILVER_SWORD
import foundry.animamundi.grimoire.AnimaMundiItems.TOXIN
import net.minecraft.data.DataGenerator
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.client.model.generators.ItemModelProvider
import net.minecraftforge.common.data.ExistingFileHelper

class AnimaMundiItemModels(generator: DataGenerator, existingFileHelper: ExistingFileHelper) : ItemModelProvider(generator, AnimaMundi.MODID,
    existingFileHelper
) {
    override fun registerModels() {
        registerBasicItemModel(SILVER_INGOT.id.path)
        registerBasicItemModel(SILVER_NUGGET.id.path)
        registerBasicItemModel(RAW_SILVER.id.path)
        registerBasicItemModel(ARAMID_FABRIC.id.path)
        registerBasicItemModel(ANTIMONY_INGOT.id.path)
        registerBasicItemModel(ANTIMONY_NUGGET.id.path)
        registerBasicItemModel(BONE_ASH.id.path)
        registerBasicItemModel(MIASMANE_INGOT.id.path)
        registerBasicItemModel(PESTLE.id.path)
        registerBasicItemModel(TOXIN.id.path)
        registerBasicItemModel(SEDATIVE.id.path)
        registerBasicItemModel(ANTIDOTE.id.path)

        registerToolItemModel(SILVER_AXE.id.path)
        registerToolItemModel(SILVER_PICKAXE.id.path)
        registerToolItemModel(SILVER_SHOVEL.id.path)
        registerToolItemModel(SILVER_HOE.id.path)
        registerToolItemModel(SILVER_SWORD.id.path)
        registerToolItemModel(ANTIMONY_AXE.id.path)
        registerToolItemModel(ANTIMONY_AXE.id.path)
        registerToolItemModel(ANTIMONY_PICKAXE.id.path)
        registerToolItemModel(ANTIMONY_SHOVEL.id.path)
        registerToolItemModel(ANTIMONY_HOE.id.path)
        registerToolItemModel(ANTIMONY_SWORD.id.path)

        registerBlockItemModel(ALEMBIC.id.path)
        registerBlockItemModel(MORTAR.id.path)
    }

    fun registerBasicItemModel(item: String){
        withExistingParent(item, ResourceLocation("minecraft", "item/generated")).texture("layer0", ResourceLocation(AnimaMundi.MODID, "item/$item"))
    }

    fun registerToolItemModel(item: String){
        withExistingParent(item, ResourceLocation("minecraft", "item/handheld")).texture("layer0", ResourceLocation(AnimaMundi.MODID, "item/$item"))
    }

    fun registerBlockItemModel(block: String){
        withExistingParent(block, AnimaMundi.path("block/$block"))
    }
}