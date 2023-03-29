package foundry.animamundi.content.item.antidote

import foundry.animamundi.extensions.ExtendedPoseExpansion
import foundry.veil.math.Easings
import foundry.veil.model.anim.IChargableItem
import foundry.veil.model.pose.ExtendedPose
import foundry.veil.model.pose.PoseRegistry
import net.minecraft.client.model.geom.ModelPart
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
import kotlin.math.sin

class AntidoteItem(props: Properties) : Item(props), IChargableItem {

    companion object{
        fun tick(player: Player){
            if(player.level.isClientSide) return
            player.getCapability(AntidoteCounterHandler.CAPABILITY).ifPresent { counter ->
                counter.tick()
            }
        }

        val pose: ExtendedPose = PoseRegistry.registerPose({ it is AntidoteItem }, object : ExtendedPoseExpansion(){
            override fun poseMainHand(mainHand: ModelPart) {
                if(!data.swapped) {
                    mainHand.xRot -= 0.5f
                    mainHand.zRot -= 1.25f
                    var mult = Math.sin((data.useTime + data.partialTick) / 2.0).toFloat()
                    mult = Easings.ease(mult, Easings.Easing.easeInOutSine)
                    mainHand.yRot -= mult
                } else{
                    mainHand.xRot -= 0.75f
                    mainHand.zRot -= 0.5f
                }
            }

            override fun poseOffHand(offHand: ModelPart) {
                if(!data.swapped) {
                    offHand.xRot -= 0.75f
                    offHand.zRot += 0.5f
                } else{
                    offHand.xRot -= 0.5f
                    offHand.zRot += 1.25f
                    var mult = sin((data.useTime + data.partialTick) / 2.0).toFloat()
                    mult = Easings.ease(mult, Easings.Easing.easeInOutSine)
                    offHand.yRot += mult
                }
            }
        })
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
        if(pLevel.isClientSide) return super.finishUsingItem(pStack, pLevel, pLivingEntity)
        (pLivingEntity is Player).let { pLivingEntity as Player }.let {
            it.getCapability(AntidoteCounterHandler.CAPABILITY).ifPresent { counter ->
                counter.addAntidoteCount(1)
                for (activeEffect in it.activeEffects) {
                    if (effects.contains(activeEffect.effect) && counter.getAntidoteCount() < 6) {
                        it.removeEffect(activeEffect.effect)
                        counter.removeAntidoteCount(1)
                    }
                }
                if(counter.getAntidoteCount() > 3){
                    it.addEffect(MobEffectInstance(MobEffects.WEAKNESS, 200, 0))
                    it.addEffect(MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0))
                }
                if(counter.getAntidoteCount() > 5){
                    it.addEffect(MobEffectInstance(MobEffects.HUNGER, 200, 0))
                }
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