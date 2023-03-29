package foundry.animamundi.happenings

import foundry.alembic.event.AlembicSetupEvent
import foundry.animamundi.AnimaMundi
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = AnimaMundi.MODID)
class CrossModEvents {

    @SubscribeEvent
    fun alembicSetup(event: AlembicSetupEvent){
        event.addDamageType("eldritch_damage")
        event.addParticle("eldritch_damage")
    }
}