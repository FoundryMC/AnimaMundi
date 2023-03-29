package foundry.animamundi.grimoire

import foundry.animamundi.AnimaMundi
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

object AnimaMundiItemGroups {
    val MAIN_GROUP: CreativeModeTab = object : CreativeModeTab("animamundi.main_group") {
        override fun makeIcon(): ItemStack {
            return ItemStack(AnimaMundiItems.SEDATIVE.get())
        }
    }
}
