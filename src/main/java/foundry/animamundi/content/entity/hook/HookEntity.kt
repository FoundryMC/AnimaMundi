package foundry.animamundi.content.entity.hook

import foundry.animamundi.extensions.div
import foundry.animamundi.grimoire.AnimaMundiNetworking
import foundry.animamundi.math.collideSphereAABB
import foundry.animamundi.extensions.minus
import foundry.animamundi.extensions.plus
import foundry.animamundi.extensions.times
import net.minecraft.core.BlockPos
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.Packet
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level
import net.minecraft.world.phys.Vec3
import net.minecraftforge.network.NetworkHooks
import net.minecraftforge.network.PacketDistributor
import kotlin.math.floor

class HookEntity(type: EntityType<*>, pLevel: Level) : Entity(type, pLevel) {

    var initialized = false
    var boundToEntity = false
    var entityBoundID: Int = 0

    /**
     * List of points in the verlet/xpbd GAX style simulation
     * Point [0] is anchored to the end of the hook
     * Point [size - 1] is anchored to the player
     */
    var points = mutableListOf<Vec3>()
    var prevPoints = mutableListOf<Vec3>()

    /**
     * Assumed distance between all rope points, or length of all "fibers" excluding the tail segment
     */
    var segmentLength = 0.5

    /**
     * Substeps in the GAX style simulation
     * Intentionally low for artificial compliance in the simulation
     */
    var substeps = 5

    /**
     * Stiffness of the simulated rope
     */
    var stiffness = 1.0

    /**
     * Radius of each point, for sphere collision
     */
    var radius = 0.2

    override fun tick() {
        super.tick()

        try {
            if (!initialized) {

                points.add(position())

                repeat(15) {
                    points.add(position().add(segmentLength * it, 0.0, 0.0))
                }

                initialized = true
            }

            if (level.isClientSide) {
                if (prevPoints.isEmpty()) prevPoints = points
                return
            }

            repeat(substeps) { substep ->

                prevPoints = points
                points = points.map {
                    var ret = it

                    for (x in floor(it.x - radius).toInt()..floor(it.x + radius).toInt()) {
                        for (y in floor(it.y - radius).toInt()..floor(it.y + radius).toInt()) {
                            for (z in floor(it.z - radius).toInt()..floor(it.z + radius).toInt()) {
                                var pos = BlockPos(x, y, z)

                                if (level.getBlockState(pos).isAir) continue

                                var manifold = collideSphereAABB(it, 0.1, Vec3.atCenterOf(pos))

                                if(manifold != null) ret = it + manifold.normal * manifold.depth
                            }
                        }
                    }

                    ret
                }.toMutableList()

                for (i in 0 until points.size - 1) {
                    var pointA = points[i]
                    var pointB = points[i + 1]

                    val fiberCentre = (pointA + pointB) / 2.0
                    val fiberDir = (pointA - pointB).normalize()
                    val length = (pointA - pointB).length()

                    // vertlet the distance
                    pointA = fiberCentre.add(fiberDir.scale(segmentLength).scale(1.0 / 2).scale(stiffness))
                    pointB = fiberCentre.subtract(fiberDir.scale(segmentLength).scale(1.0 / 2).scale(stiffness))

                    points[i] = pointA
                    points[i + 1] = pointB
                }

                val dt = 1 / 20.0

                points = points.mapIndexed { index, point ->
                    var ret = point
                    ret += (ret - prevPoints[index].scale(0.95))
                    ret += (Vec3(0.0, -1.0, 0.0).scale(5 * dt * dt))
                    ret
                }.toMutableList()
            }

            AnimaMundiNetworking.CHANNEL.send(
                PacketDistributor.TRACKING_CHUNK.with { level.getChunkAt(blockPosition()) },
                HookSyncPacket(id, points)
            )
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override fun defineSynchedData() {

    }

    override fun readAdditionalSaveData(pCompound: CompoundTag) {

    }

    override fun addAdditionalSaveData(pCompound: CompoundTag) {

    }

    override fun getAddEntityPacket(): Packet<*> = NetworkHooks.getEntitySpawningPacket(this)

}