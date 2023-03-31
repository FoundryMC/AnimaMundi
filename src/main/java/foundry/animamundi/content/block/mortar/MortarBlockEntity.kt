package foundry.animamundi.content.block.mortar

import foundry.animamundi.util.TooltipHelper
import foundry.veil.color.Color
import foundry.veil.color.ColorTheme
import foundry.veil.ui.Tooltippable
import foundry.veil.ui.VeilUIItemTooltipDataHolder
import foundry.veil.ui.anim.TooltipKeyframe
import foundry.veil.ui.anim.TooltipTimeline
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import kotlin.math.cos
import kotlin.math.sin

class MortarBlockEntity(pType: BlockEntityType<*>, pPos: BlockPos, pBlockState: BlockState) : BlockEntity(pType, pPos,
    pBlockState
), Tooltippable {
    private var tooltip: MutableList<Component> = mutableListOf()
    private var progress: Int = 0
    private var theme: ColorTheme = ColorTheme().also { it.addColor("background", Color(104,110,104).multiply(1.0f,1.0f,1.0f,0.75f))
        it.addColor("topBorder", Color(143,104,74))
        it.addColor("bottomBorder", Color(90,65,43)) }

    init {
        tooltip.add(Component.translatable("block.animamundi.mortar"))
        tooltip.add(Component.translatable("block.animamundi.mortar.progress_tooltip", progress))
        tooltip.add(Component.empty())
        tooltip.add(Component.literal("            →        "))
    }

    fun tick(pLevel: Level, pPos: BlockPos, pState: BlockState, mortarBlockEntity: MortarBlockEntity) {
        if(progress >= 100) {
            progress = 0
        }
        tooltip[1] = TooltipHelper.makeProgressBar(progress/100f, theme.getColor("topBorder").rgb, theme.getColor("bottomBorder").rgb)
        pLevel.sendBlockUpdated(pPos, pState, pState, 3)
    }

    fun use(pState: BlockState,
            pLevel: Level,
            pPos: BlockPos,
            pPlayer: Player,
            pHand: InteractionHand,
            pHit: BlockHitResult
    ): InteractionResult {
        progress += 1
        level?.playLocalSound(pPos.x.toDouble(), pPos.y.toDouble(), pPos.z.toDouble(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 0.75f, Math.random().toFloat(), false)
        level?.sendBlockUpdated(pPos, pState, pState, 3)
        return InteractionResult.SUCCESS
    }
    override fun getTooltip(): MutableList<Component> {
        return tooltip
    }

    override fun setTooltip(p0: MutableList<Component>?) {
        tooltip = p0!!
    }

    override fun addTooltip(p0: Component?) {
        tooltip.add(p0!!)
    }

    override fun addTooltip(p0: MutableList<Component>?) {
        tooltip.addAll(p0!!)
    }

    override fun addTooltip(p0: String?) {
        tooltip.add(Component.translatable(p0!!))
    }

    override fun getTheme(): ColorTheme {
        return theme
    }

    override fun setTheme(p0: ColorTheme?) {
        theme = p0!!
    }

    override fun setBackgroundColor(p0: Int) {
        theme.addColor("background", Color(p0))
    }

    override fun setTopBorderColor(p0: Int) {
        theme.addColor("topBorder", Color(p0))
    }

    override fun setBottomBorderColor(p0: Int) {
        theme.addColor("bottomBorder", Color(p0))
    }

    override fun getWorldspace(): Boolean {
        return true
    }

    override fun getTimeline(): TooltipTimeline {
        return TooltipTimeline(arrayOf(TooltipKeyframe()), 1.0f)
    }

    override fun getStack(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun getTooltipWidth(): Int {
        return 0
    }

    override fun getTooltipHeight(): Int {
        return 8
    }

    override fun getTooltipXOffset(): Int {
        return -10
    }

    override fun getTooltipYOffset(): Int {
        return 15
    }

    override fun getItems(): MutableList<VeilUIItemTooltipDataHolder> {
        return mutableListOf(
            VeilUIItemTooltipDataHolder(Items.BONE.defaultInstance, { 28f }, { -15f }),
            VeilUIItemTooltipDataHolder(Items.BONE_MEAL.defaultInstance, { 58f }, { -15f })
        )
    }
}