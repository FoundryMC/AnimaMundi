package foundry.animamundi.content.compat.alembic;

import foundry.alembic.types.tags.AlembicTag;
import foundry.alembic.types.tags.AlembicTagType;

public interface AnimaMundiTagType<T extends AlembicTag> extends AlembicTagType<T> {
    AnimaMundiTagType<MobTypeDamageBonusTag> MOB_TYPE_DAMAGE_BONUS = MobTypeDamageBonusTag.Companion::getCODEC;
}
