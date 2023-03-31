package foundry.animamundi.content.block.alembic

import com.mojang.blaze3d.vertex.PoseStack
import foundry.veil.ui.UIUtils
import net.minecraft.client.gui.Font
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.world.item.ItemStack

class AlembicBlockEntityRenderer(context: BlockEntityRendererProvider.Context): BlockEntityRenderer<AlembicBlockEntity> {
    private var font: Font = context.font
    override fun render(
        pBlockEntity: AlembicBlockEntity,
        pPartialTick: Float,
        ps: PoseStack,
        pBufferSource: MultiBufferSource,
        pPackedLight: Int,
        pPackedOverlay: Int
    ) {
        ps.pushPose()
        ps.translate(0.5, 0.5, 0.5)
        //UIUtils.drawHoverText(ItemStack.EMPTY, ps, pBlockEntity.tooltip, pBlockEntity.blockPos.x, pBlockEntity.blockPos.y, pBlockEntity.blockPos.z, 0, 0, 50, pBlockEntity.theme.getColor("background").hex, pBlockEntity.theme.getColor("topBorder").hex, pBlockEntity.theme.getColor("bottomBorder").hex, font)
        ps.popPose()
    }
}