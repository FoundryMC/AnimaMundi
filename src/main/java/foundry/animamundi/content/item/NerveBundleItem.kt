package foundry.animamundi.content.item

import foundry.animamundi.content.brain.cortex.Cortex
import foundry.animamundi.content.brain.cortex.block.CortexBlock
import foundry.animamundi.content.brain.cortex.block.CortexBlockEntity
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.NbtUtils
import net.minecraft.network.chat.ChatType
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.InteractionResultHolder
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Level

class NerveBundleItem(props: Properties) : Item(props) {


    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level

        if (level.isClientSide) return super.useOn(context);

        val tag: CompoundTag = context.getItemInHand()
            .getOrCreateTag()

        val pos = context.clickedPos
        val block = level.getBlockState(pos).block
        if (block is CortexBlock) {
            level.getBlockEntity(pos)?.let { entity ->
                if (entity is CortexBlockEntity) {

                    if (tag.contains("First")) {
                        val firstPos = NbtUtils.readBlockPos(tag.getCompound("First"))
                        // first be
                        val firstEntity = level.getBlockEntity(firstPos)

                        if (firstEntity is CortexBlockEntity) {
                            addConnection(context.player as ServerPlayer, firstEntity, entity)
                        }

                        tag.remove("First")
                        return InteractionResult.CONSUME
                    } else {
                        tag.put("First", NbtUtils.writeBlockPos(pos))

                        val component = Component.literal("Started connection")
                        context.player!!.displayClientMessage(component, true)

                        return InteractionResult.SUCCESS
                    }
                }
            }
        }


        return super.useOn(context)
    }

    private fun addConnection(player: ServerPlayer, first: CortexBlockEntity, second: CortexBlockEntity) {
        val a = first.cortex!!
        val b = second.cortex!!


        a.outputs.forEach { outType ->
            b.inputs.forEach { (str, type) ->
                if (type.allowed(outType)) {
                    b.inputDependencies[str] = Cortex.Dependency(first.blockPos, outType)

                    val component = Component.literal("Connected ${first.blockPos} to ${second.blockPos}").withStyle(ChatFormatting.GREEN)
                    player.displayClientMessage(component, true)

                    return;
                }
            }
        }

        val component = Component.literal("Connection failed").withStyle(ChatFormatting.RED)
        player.displayClientMessage(component, true)
    }

    override fun use(pLevel: Level, pPlayer: Player, pUsedHand: InteractionHand): InteractionResultHolder<ItemStack> {
        return super.use(pLevel, pPlayer, pUsedHand)
    }
}