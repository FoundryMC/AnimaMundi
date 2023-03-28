package foundry.animamundi.grimoire

import com.tterrag.registrate.util.entry.ItemEntry
import foundry.animamundi.AnimaMundi
import foundry.animamundi.content.item.SimpleGrapplingHook
import net.minecraft.world.item.Item.Properties

object AnimaMundiItems {

    private val REGISTRATE = AnimaMundi.registrate()
        .creativeModeTab { AnimaMundiItemGroups.MAIN_GROUP }

    fun register() {}

    val SIMPLE_GRAPPLING_HOOK = REGISTRATE.item<SimpleGrapplingHook>("simple_grappling_hook") { props: Properties -> SimpleGrapplingHook(props) }.register()

}