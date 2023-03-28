package foundry.animamundi.math

import foundry.animamundi.closestPoint
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

fun collideSphereAABB(point: Vec3, radius: Double, blockPos: Vec3): Manifold? {
    val aabb = AABB(blockPos.subtract(0.5, 0.5, 0.5), blockPos.add(0.5, 0.5, 0.5))
    if (point.distanceToSqr(aabb.center) < 0.001) return null

    // Test for collision
    val closestPointA: Vec3 = aabb.closestPoint(point)
    val distance = point.distanceToSqr(closestPointA)

    return if (distance < radius * radius) {
        // Collision detected
        val normal = point.subtract(closestPointA).normalize()
        val penetration = radius - Math.sqrt(distance)
        if (penetration < 0) {
            return null
        }

        // Position of the collision point
        val collisionPointA = point.add(normal.scale(-radius))
        Manifold(normal.scale(1.0), penetration, collisionPointA, closestPointA)
    } else {
        null
    }
}