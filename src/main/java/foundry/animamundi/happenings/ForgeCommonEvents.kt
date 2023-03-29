@file:EventBusSubscriber(modid = AnimaMundi.MODID, bus = EventBusSubscriber.Bus.FORGE)

package foundry.animamundi.happenings

import foundry.animamundi.AnimaMundi
import foundry.animamundi.content.item.antidote.AntidoteCounterProvider
import foundry.animamundi.content.item.antidote.AntidoteItem
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.TickEvent.PlayerTickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

object ForgeCommonEvents{
    fun attachCaps(event: AttachCapabilitiesEvent<Entity>) {
        event.addCapability(AnimaMundi.path("antidote_counter"), AntidoteCounterProvider())
    }
    fun playerTick(event: PlayerTickEvent) {
        AntidoteItem.tick(event.player)
    }
}