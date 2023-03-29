package foundry.animamundi

import com.mojang.logging.LogUtils
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.util.nullness.NonNullSupplier
import foundry.animamundi.AnimaMundi
import foundry.animamundi.content.compat.alembic.CodecUtil
import foundry.animamundi.grimoire.AnimaMundiEntities
import foundry.animamundi.grimoire.AnimaMundiItems.register
import foundry.animamundi.grimoire.AnimaMundiNetworking
import net.minecraft.resources.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.TickEvent.ServerTickEvent
import net.minecraftforge.event.server.ServerStartingEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventBusSubscriber
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist

@Mod(AnimaMundi.MODID)
class AnimaMundi {
    init {
        MinecraftForge.EVENT_BUS.register(this)
        val modEventBus = FMLJavaModLoadingContext.get().modEventBus
        CodecUtil.init()
        register()
        AnimaMundiEntities.register()
        AnimaMundiNetworking.init()

        runForDist(
            clientTarget = {
            AnimaMundiClient.init()
            },
            serverTarget = {

            })
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {}
    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {
    }

    @SubscribeEvent
    fun onServerTick(event: ServerTickEvent?) {
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
    object ClientModEvents {
        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent?) {
        }
    }

    companion object {
        const val MODID = "animamundi"
        private val LOGGER = LogUtils.getLogger()
        val REGISTRATE = NonNullSupplier.lazy { Registrate.create(MODID) }
        @JvmStatic
        fun registrate(): Registrate {
            return REGISTRATE.get()
        }

        @JvmStatic
        fun path(path: String?): ResourceLocation {
            return ResourceLocation(MODID, path)
        }
    }
}