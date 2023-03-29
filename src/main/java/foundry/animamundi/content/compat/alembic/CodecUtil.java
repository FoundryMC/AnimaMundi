package foundry.animamundi.content.compat.alembic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import foundry.alembic.types.tags.AlembicTagRegistry;
import foundry.animamundi.AnimaMundi;
import net.minecraft.world.entity.MobType;

import java.util.Map;

public class CodecUtil {
    public static final Map<String, MobType> MOB_TYPE_MAP = Map.of(
            "UNDEFINED", MobType.UNDEFINED,
            "UNDEAD", MobType.UNDEAD,
            "ARTHROPOD", MobType.ARTHROPOD,
            "ILLAGER", MobType.ILLAGER,
            "WATER", MobType.WATER
    );
    public static final Codec<MobType> MOB_TYPE_CODEC = Codec.STRING.comapFlatMap(s -> {
        MobType mobType = MOB_TYPE_MAP.get(s);
        if (mobType == null) {
            return DataResult.error("Invalid mob type: " + s);
        }
        return DataResult.success(mobType);
    }, mobType -> {
        for (Map.Entry<String, MobType> entry : MOB_TYPE_MAP.entrySet()) {
            if (entry.getValue() == mobType) {
                return entry.getKey();
            }
        }
        return "UNDEFINED";
    });

    public static void init(){
        AlembicTagRegistry.register(AnimaMundi.path("mob_type_damage_bonus"), AnimaMundiTagType.MOB_TYPE_DAMAGE_BONUS);
    }
}
