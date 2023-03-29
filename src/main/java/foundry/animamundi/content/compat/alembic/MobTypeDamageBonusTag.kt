package foundry.animamundi.content.compat.alembic

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import foundry.alembic.types.tags.AlembicTag
import foundry.alembic.types.tags.AlembicTagType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.MobType
import net.minecraft.world.item.Item
import net.minecraftforge.registries.ForgeRegistries

class MobTypeDamageBonusTag(
    private val mobType: MobType,
    private val damageBonus: Float,
    private val damageBonusChance: Float,
    private val itemIDs: List<String>
): AlembicTag {
    companion object{
        val CODEC: Codec<MobTypeDamageBonusTag> = RecordCodecBuilder.create { instance: RecordCodecBuilder.Instance<MobTypeDamageBonusTag> ->
            instance.group(
                CodecUtil.MOB_TYPE_CODEC.fieldOf("mob_type").forGetter(MobTypeDamageBonusTag::mobType),
                Codec.FLOAT.fieldOf("damage_bonus").forGetter(MobTypeDamageBonusTag::damageBonus),
                Codec.FLOAT.fieldOf("damage_bonus_chance").forGetter(MobTypeDamageBonusTag::damageBonusChance),
                Codec.STRING.listOf().fieldOf("items").forGetter(MobTypeDamageBonusTag::itemIDs)
            ).apply(instance, ::MobTypeDamageBonusTag)}
    }
    override fun run(composedData: AlembicTag.ComposedData) {
        val target: LivingEntity = composedData.get(AlembicTag.ComposedDataType.TARGET_ENTITY)
        val attackerItem: Item = composedData.get(AlembicTag.ComposedDataType.ORIGINAL_SOURCE).entity.let { it as LivingEntity }.mainHandItem.item
        var damage: Float = composedData.get(AlembicTag.ComposedDataType.FINAL_DAMAGE)
        if(itemIDs.contains(ForgeRegistries.ITEMS.getKey(attackerItem).toString())){
            if(target.mobType == mobType){
                if(Math.random() < damageBonusChance){
                    damage += damageBonus
                }
            }
        }
    }

    override fun getType(): AlembicTagType<*> {
        return AnimaMundiTagType.MOB_TYPE_DAMAGE_BONUS
    }
}