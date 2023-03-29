package foundry.animamundi.content.item.grapple

import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level

abstract class GrapplingHookItem(props: Properties) : Item(props) {

    override fun use(level: Level, player: Player, usedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        return super.use(level, player, usedHand)
    }

    abstract fun range(): Double

}