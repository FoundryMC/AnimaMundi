package foundry.animamundi.content.item.antidote

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.math.Vector3f
import foundry.animamundi.extensions.ExtendedPoseExpansion
import foundry.veil.math.Easings
import foundry.veil.model.pose.ExtendedPose
import foundry.veil.model.pose.PoseRegistry
import net.minecraft.client.Minecraft
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.renderer.ItemInHandRenderer
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import kotlin.math.cos
import kotlin.math.sin

class AntidoteClient {
    companion object {
        fun init (){}
        val pose: ExtendedPose = PoseRegistry.registerPose({ it is AntidoteItem }, object : ExtendedPoseExpansion() {
            override fun forceRenderMainHand(): Boolean {
                return data.useTime != data.maxUseTime
            }

            override fun forceRenderOffhand(): Boolean {
                val isOffhandEmpty = Minecraft.getInstance().player?.offhandItem?.item == Items.AIR
                return data.useTime != data.maxUseTime && isOffhandEmpty
            }

            override fun overrideItemTransform(): Boolean {
                return data.useTime != data.maxUseTime && data.useTime != 0.0f
            }

            override fun poseMainHandFirstPerson(stack: PoseStack) {
                if (data.useTime != data.maxUseTime) {
                    stack.mulPose(Vector3f.XP.rotationDegrees((cos(data.useTime + data.partialTick) * 10) + 15.0f))
                    stack.mulPose(Vector3f.YP.rotationDegrees((sin(data.useTime + data.partialTick) * 15) + 30.0f))
                    stack.translate(0.0, 0.25, 0.15)
                }
            }

            override fun poseOffHandFirstPerson(stack: PoseStack) {
                if (data.useTime != data.maxUseTime) {
                    stack.mulPose(Vector3f.YN.rotationDegrees(15.0f))
                    stack.translate(0.0, 0.25, 0.0)
                }
            }

            override fun poseItem(itemRenderer: ItemInHandRenderer?) {
                data.stackPoseStack.scale(3.0f, 3.0f, 3.0f)
                data.stackPoseStack.translate(0.0, 0.0, 0.0)
            }

            override fun poseMainHand(mainHand: ModelPart) {
                if (!data.swapped) {
                    mainHand.xRot = -0.5f
                    mainHand.zRot = -1.25f
                    var mult = sin((data.useTime + data.partialTick) / 2.0).toFloat()
                    mult = Easings.ease(mult, Easings.Easing.easeInOutSine)
                    mainHand.yRot = -mult
                } else {
                    mainHand.xRot = -0.75f
                    mainHand.zRot = -0.5f
                }
            }

            override fun poseOffHand(offHand: ModelPart) {
                if (!data.swapped) {
                    offHand.xRot = -0.75f
                    offHand.zRot = 0.5f
                } else {
                    offHand.xRot = -0.5f
                    offHand.zRot = 1.25f
                    var mult = sin((data.useTime + data.partialTick) / 2.0).toFloat()
                    mult = Easings.ease(mult, Easings.Easing.easeInOutSine)
                    offHand.yRot = mult
                }
            }
        })
    }
}