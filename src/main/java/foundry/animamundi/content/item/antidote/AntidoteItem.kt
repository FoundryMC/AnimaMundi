package foundry.animamundi.content.item.antidote

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import foundry.animamundi.extensions.ExtendedPoseExpansion
import foundry.veil.math.Easings
import foundry.veil.model.anim.IChargableItem
import foundry.veil.model.pose.ExtendedPose
import foundry.veil.model.pose.PoseRegistry
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.ItemInHandRenderer
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.ItemUtils
import net.minecraft.world.item.UseAnim
import net.minecraft.world.level.Level
import kotlin.math.cos
import kotlin.math.sin

class AntidoteItem(props: Properties) : Item(props), IChargableItem {

    companion object {
        fun tick(player: Player) {
            if (player.level.isClientSide) return
            player.getCapability(AntidoteCounterHandler.CAPABILITY).ifPresent { counter ->
                counter.tick(player)
            }
        }
    }

    private val effects: List<MobEffect> = listOf(
        MobEffects.POISON, MobEffects.HUNGER, MobEffects.CONFUSION
    )

    override fun getEatingSound(): SoundEvent {
        return SoundEvents.BOOK_PAGE_TURN
    }

    override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        return ItemUtils.startUsingInstantly(pLevel, pPlayer, pUsedHand)
    }

    override fun finishUsingItem(pStack: ItemStack, pLevel: Level, pLivingEntity: LivingEntity): ItemStack {
        if (pLevel.isClientSide) return super.finishUsingItem(pStack, pLevel, pLivingEntity)
        (pLivingEntity is Player).let { pLivingEntity as Player }.let {
            it.getCapability(AntidoteCounterHandler.CAPABILITY).ifPresent { counter ->
                counter.addAntidoteCount(1)
                for (activeEffect in it.activeEffects) {
                    if (effects.contains(activeEffect.effect) && counter.getAntidoteCount() < 6) {
                        it.removeEffect(activeEffect.effect)
                        it.displayClientMessage(Component.literal("Removed ${activeEffect.effect.displayName}!"), true)
                        counter.removeAntidoteCount(1)
                    }
                }
                if (counter.getAntidoteCount() > 3) {
                    it.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 200, 0))
                    it.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0))
                    it.displayClientMessage(Component.literal("You feel weak."), true)
                }
                if (counter.getAntidoteCount() > 5) {
                    it.addEffect(MobEffectInstance(MobEffects.HUNGER, 200, 0))
                    it.displayClientMessage(Component.literal("Maybe you should stop..."), true)
                }
                pStack.shrink(1)
            }
        }
        return super.finishUsingItem(pStack, pLevel, pLivingEntity)
    }

    override fun getUseDuration(pStack: ItemStack): Int {
        return 20
    }

    override fun getUseAnimation(pStack: ItemStack): UseAnim {
        return UseAnim.EAT
    }

    override fun getMaxCharge(): Int {
        return 20
    }

    override fun getCharge(): Int {
        return getUseDuration(this.defaultInstance)
    }

    override fun setCharge(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun addCharge(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun removeCharge(p0: Int) {
        TODO("Not yet implemented")
    }
}