package foundry.animamundi.grimoire

import foundry.animamundi.AnimaMundi
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

object AnimaMundiItemGroups {
    val MAIN_GROUP: CreativeModeTab = object : CreativeModeTab("main_group") {
        override fun makeIcon(): ItemStack {
            return ItemStack(AnimaMundiItems.SIMPLE_GRAPPLING_HOOK.get())
        }
    }
}
