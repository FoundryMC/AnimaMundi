package foundry.animamundi;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import foundry.animamundi.grimoire.AnimaMundiEntities;
import foundry.animamundi.grimoire.AnimaMundiItems;
import foundry.animamundi.grimoire.AnimaMundiNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import static org.openjdk.nashorn.internal.objects.ArrayBufferView.length;

@Mod(AnimaMundi.MODID)
public class AnimaMundi {

    public static final String MODID = "animamundi";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(() -> Registrate.create(MODID));

    public static Registrate registrate() {
        return REGISTRATE.get();
    }

    public static ResourceLocation path(String path) {
        return new ResourceLocation(MODID, path);
    }

    public AnimaMundi() {
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AnimaMundiItems.INSTANCE.register();
        AnimaMundiEntities.register();
        AnimaMundiNetworking.init();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

//    double smin(double a, double b, double k) {
//        double h = Mth.clamp(0.5 + 0.5*(a-b)/k, 0.0, 1.0);
//        return (a + (b - a) * h) - k*h*(1.0-h);
//    }

    double smin(double a, double b, double smoothness) {
        if (smoothness == 0) {
            return Math.min(a, b);
        } else {
            double k = 1 / smoothness;
            double res = Math.pow(2, -k * a) + Math.pow(2, -k * b);
            return -(Math.log10(res) / Math.log10(2)) / k;
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {

        LocalPlayer player = Minecraft.getInstance().player;


        if (player != null && player.swingTime < 1 && player.swinging && player.isShiftKeyDown()) {
            Level level = player.level;
            BlockHitResult clip = level.clip(new ClipContext(
                    player.getEyePosition(),
                    player.getEyePosition().add(player.getLookAngle().scale(50.0)),
                    ClipContext.Block.COLLIDER,
                    ClipContext.Fluid.NONE,
                    player
            ));

            BlockPos center = clip.getBlockPos();

            BoundingBox box = new BoundingBox(center).inflatedBy(8);

            HashMap<BlockPos, Double> sdf = new HashMap<>();
            HashMap<BlockPos, BlockState> cache = new HashMap<>();

            Iterable<BlockPos> closed1 = BlockPos.betweenClosed(new BlockPos(box.minX(), box.minY(), box.minZ()), new BlockPos(box.maxX(), box.maxY(), box.maxZ()));

            for (BlockPos mutPos : closed1) {
                BlockState state = cache.computeIfAbsent(new BlockPos(mutPos), level::getBlockState);

                boolean desiredIsAir = !state.isAir();

                BoundingBox curScanBox = new BoundingBox(new BlockPos(mutPos));

                double minDistSqr = 100000.0;
                boolean sentinel = true;

                // \/ \/ \/ fucking moronic
                while(sentinel) {
                    Iterable<BlockPos> closedInternal = BlockPos.betweenClosed(new BlockPos(curScanBox.minX(), curScanBox.minY(), curScanBox.minZ()), new BlockPos(curScanBox.maxX(), curScanBox.maxY(), curScanBox.maxZ()));

                    for (BlockPos mutPosInternal : closedInternal) {
                        BlockState stateInternal = cache.computeIfAbsent(new BlockPos(mutPosInternal), level::getBlockState);

                        if(stateInternal.isAir() == desiredIsAir) {
                            minDistSqr = Math.min(mutPosInternal.distSqr(new BlockPos(mutPos)), minDistSqr);
                            sentinel = false;
                        }
                    }

                    curScanBox = curScanBox.inflatedBy(1);
                }

                double minDist = Math.sqrt(minDistSqr);

                if(desiredIsAir) minDist *= -1.0;

                sdf.put(new BlockPos(mutPos), minDist);
            }

            for (Map.Entry<BlockPos, Double> entry : sdf.entrySet()) {
                BlockPos pos = entry.getKey();

                double dist = entry.getValue();

                Vec3 centerBox = Vec3.atLowerCornerOf(center.offset(new Vec3i(0, 0, 0)));
                Vec3 camera = Vec3.atLowerCornerOf(pos).subtract(centerBox);
                Vec3 q = new Vec3(Math.abs(camera.x), Math.abs(camera.y), Math.abs(camera.z)).subtract(new Vec3(4, 4, 4));
                double sphereDist = (new Vec3(Math.max(q.x, 0), Math.max(q.y, 0), Math.max(q.z, 0))).length() + Math.min(Math.max(q.x,Math.max(q.y,q.z)),0.0);


                double smoothMin = smin(dist, sphereDist, 2.5);

                if(smoothMin < 0.1)
                    event.getServer().overworld().setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
//                else
//                    event.getServer().overworld().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
