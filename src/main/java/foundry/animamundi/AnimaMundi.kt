package foundry.animamundi

import com.mojang.logging.LogUtils
import com.tterrag.registrate.Registrate
import com.tterrag.registrate.util.nullness.NonNullSupplier
import foundry.animamundi.AnimaMundi
import foundry.animamundi.content.compat.alembic.CodecUtil
import foundry.animamundi.grimoire.AnimaMundiEntities
import foundry.animamundi.grimoire.AnimaMundiItems.register
import foundry.animamundi.grimoire.AnimaMundiNetworking
import foundry.animamundi.happenings.ForgeCommonEvents
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
object AnimaMundi {
    public const val MODID = "animamundi"
    public val LOGGER = LogUtils.getLogger()
    public val REGISTRATE = NonNullSupplier.lazy { Registrate.create(MODID) }

    fun registrate(): Registrate {
        return REGISTRATE.get()
    }

    fun path(path: String?): ResourceLocation {
        return ResourceLocation(MODID, path)
    }

    init {
        CodecUtil.init()
        register()
        AnimaMundiEntities.register()
        AnimaMundiNetworking.init()

        val obj = runForDist(
            clientTarget = {},
            serverTarget = {
                MOD_BUS.addListener(ForgeCommonEvents::attachCaps)
                MOD_BUS.addListener(ForgeCommonEvents::playerTick)
            }
        )
    }

}