package foundry.animamundi.content.block.alembic

import foundry.animamundi.grimoire.AnimaMundiBlocks
import foundry.animamundi.grimoire.AnimaMundiItems
import foundry.animamundi.util.TooltipHelper
import foundry.veil.color.Color
import foundry.veil.color.ColorTheme
import foundry.veil.ui.Tooltippable
import foundry.veil.ui.VeilUIItemTooltipDataHolder
import foundry.veil.ui.anim.TooltipKeyframe
import foundry.veil.ui.anim.TooltipTimeline
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class AlembicBlockEntity(pType: BlockEntityType<*>, pPos: BlockPos, pBlockState: BlockState) : BlockEntity(pType, pPos,
    pBlockState
), Tooltippable {
    private var tooltip: MutableList<Component> = mutableListOf()
    private var progress: Int = 0
    private var theme: ColorTheme = ColorTheme().also { it.addColor("background", Color(131,68,45).multiply(1.0f,1.0f,1.0f,0.75f))
        it.addColor("topBorder", Color(248, 204, 161))
        it.addColor("bottomBorder", Color(196,118,77)) }

    init {
        tooltip.add(Component.translatable("block.animamundi.alembic"))
        tooltip.add(Component.translatable("block.animamundi.alembic.progress_tooltip", progress / 20))
        tooltip.add(Component.empty())
        tooltip.add(Component.empty())
        tooltip.add(Component.literal("             →        "))
    }

    fun tick(pLevel: Level, pPos: BlockPos, pState: BlockState, alembicBlockEntity: AlembicBlockEntity) {
        if(progress/20 < 100) {
            progress++
        }
        if(progress/20 >= 100) {
            progress = 0
        }
        tooltip[1] = TooltipHelper.makeProgressBar(progress / 2000f, theme.getColor("topBorder").rgb, theme.getColor("bottomBorder").rgb)
        pLevel.sendBlockUpdated(pPos, pState, pState, 3)
    }
    override fun getTooltip(): MutableList<Component> {
        return tooltip
    }

    override fun setTooltip(p0: MutableList<Component>?) {
        if (p0 != null) {
            tooltip = p0
        }
    }

    override fun addTooltip(p0: Component?) {
        if (p0 != null) {
            tooltip.add(p0)
        }
    }

    override fun addTooltip(p0: MutableList<Component>?) {
        if (p0 != null) {
            tooltip.addAll(p0)
        }
    }

    override fun addTooltip(p0: String?) {
        if (p0 != null) {
            tooltip.add(Component.nullToEmpty(p0))
        }
    }

    override fun getTheme(): ColorTheme {
        return theme
    }

    override fun setTheme(p0: ColorTheme) {
        theme = p0
    }

    override fun setBackgroundColor(p0: Int) {
        theme.colors.add(0, Color.of(p0))
    }

    override fun setTopBorderColor(p0: Int) {
        theme.colors.add(1, Color.of(p0))
    }

    override fun setBottomBorderColor(p0: Int) {
        theme.colors.add(2, Color.of(p0))
    }

    override fun getWorldspace(): Boolean {
        return true
    }

    override fun getTimeline(): TooltipTimeline {
        val keyframes: Array<TooltipKeyframe> = arrayOf(TooltipKeyframe(), TooltipKeyframe())
        val timeline = TooltipTimeline(keyframes, 1.0f)
        val keyframe: TooltipKeyframe = TooltipKeyframe().also {
            it.tooltipTextWidthBonus = 0.0f
            it.tooltipTextHeightBonus = 0.0f
            it.backgroundColor = Color.VANILLA_TOOLTIP_BACKGROUND.multiply(1.0f,1.0f,1.0f,0.75f)
        }
        val keyframe2: TooltipKeyframe = TooltipKeyframe().also {
            it.tooltipTextWidthBonus = sin(progress.toFloat() / 20f * 2f * Mth.PI.toFloat())+1
            it.tooltipTextHeightBonus = sin(progress.toFloat() / 20f * 2f * Mth.PI.toFloat())+1
            it.backgroundColor = Color.RED.multiply(1.0f,1.0f,1.0f,0.75f)
        }
        timeline.addFrameForTime(keyframe, 50f)
        timeline.addFrameForTime(keyframe2, 500f)
        return timeline
    }

    override fun getStack(): ItemStack {
        return AnimaMundiBlocks.ALEMBIC.asStack()
    }

    override fun getTooltipWidth(): Int {
        return 0
    }

    override fun getTooltipHeight(): Int {
        return 16
    }

    override fun getTooltipXOffset(): Int {
        return -10
    }

    override fun getTooltipYOffset(): Int {
        return 15
    }

    override fun getItems(): MutableList<VeilUIItemTooltipDataHolder> {
        return mutableListOf(
            VeilUIItemTooltipDataHolder(Items.SPIDER_EYE.defaultInstance, { 0+cos((level!!.gameTime+it)/7f) }, { -22+sin((level!!.gameTime+it)/7f) }),
            VeilUIItemTooltipDataHolder(Items.GLISTERING_MELON_SLICE.defaultInstance, { 16+sin((level!!.gameTime+it)/7f) }, { -26+cos((level!!.gameTime+it)/7f) }),
            VeilUIItemTooltipDataHolder(Items.SUGAR.defaultInstance, { 32+cos((level!!.gameTime+it)/7f) }, { -22+sin((level!!.gameTime+it)/7f) }),
            VeilUIItemTooltipDataHolder(Items.GOLDEN_CARROT.defaultInstance, { 0+sin((level!!.gameTime+it)/7f) }, { -6+cos((level!!.gameTime+it)/7f) }),
            VeilUIItemTooltipDataHolder(Items.RABBIT_FOOT.defaultInstance, { 16+cos((level!!.gameTime+it)/7f) }, { -2+sin((level!!.gameTime+it)/7f) }),
            VeilUIItemTooltipDataHolder(Items.NETHER_WART.defaultInstance, { 32+sin((level!!.gameTime+it)/7f) }, { -6+cos((level!!.gameTime+it)/7f) }),
            VeilUIItemTooltipDataHolder(Items.PAPER.defaultInstance, { 48f }, { -26f }),
            VeilUIItemTooltipDataHolder(AnimaMundiItems.ANTIDOTE.asStack(), { 64f }, { -16f })
        )
    }

    override fun saveAdditional(pTag: CompoundTag) {
        super.saveAdditional(pTag)
        pTag.putInt("progress", progress)
    }

    override fun load(pTag: CompoundTag) {
        super.load(pTag)
        progress = pTag.getInt("progress")
    }
}