package foundry.animamundi.data

import foundry.animamundi.AnimaMundi
import foundry.animamundi.content.item.AnimaMundiItemMaterials
import net.minecraft.data.DataGenerator
import net.minecraftforge.data.event.GatherDataEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod.EventBusSubscriber

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD, modid = AnimaMundi.MODID)
class DataGenerators {

    @SubscribeEvent
    fun gatherData(event: GatherDataEvent){
        var generator = event.generator
        var existingFileHelper = event.existingFileHelper
        generator.addProvider(event.includeClient(), AnimaMundiItemModels(generator, existingFileHelper))
    }
}