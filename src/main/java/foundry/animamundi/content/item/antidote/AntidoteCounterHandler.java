package foundry.animamundi.content.item.antidote;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class AntidoteCounterHandler {
    public static Capability<AntidoteCounter> CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});
}
