package foundry.animamundi.content.entity.hook

import net.minecraft.client.Minecraft
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.phys.Vec3
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class HookSyncPacket {
    var id = 0
    var points = mutableListOf<Vec3>()

    constructor(id: Int, points: MutableList<Vec3>) {
        this.points = points
        this.id = id
    }

    fun writeVector(buffer: FriendlyByteBuf, vector: Vec3) {
        buffer.writeDouble(vector.x())
        buffer.writeDouble(vector.y())
        buffer.writeDouble(vector.z())
    }

    fun readVector(buffer: FriendlyByteBuf): Vec3 {
        return Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble())
    }

    constructor(buffer: FriendlyByteBuf) {
        id = buffer.readInt()

        val size = buffer.readInt()
        for(i in 0 until size) {
            points.add(readVector(buffer))
        }
    }

    fun encode(buffer: FriendlyByteBuf) {
        buffer.writeInt(id)
        buffer.writeInt(points.size)

        for (point in points) {
            writeVector(buffer, point)
        }
    }

    companion object {
        @JvmStatic
        fun handle(msg: HookSyncPacket, ctx: Supplier<NetworkEvent.Context>) {
            ctx.get().enqueueWork {
                // Make sure it's only executed on the physical client
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT) {
                    Runnable {

                        // Obtain the worldshell
                        val hook = Minecraft.getInstance().level!!.getEntity(msg.id)

                        if (hook is HookEntity) {
                            hook.prevPoints = hook.points
                            hook.points = msg.points
                        }
                    }
                }
            }
            ctx.get().packetHandled = true
        }
    }
}