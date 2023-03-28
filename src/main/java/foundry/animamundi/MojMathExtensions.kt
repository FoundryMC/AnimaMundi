package foundry.animamundi

import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

operator fun Vec3.plus(other: Vec3) = this.add(other)
operator fun Vec3.minus(other: Vec3) = this.subtract(other)
operator fun Vec3.times(other: Vec3) = this.multiply(other)
operator fun Vec3.div(other: Vec3) = this.multiply(Vec3(1.0 / other.x, 1.0 / other.y, 1.0 / other.z))
operator fun Vec3.times(other: Number) = this.scale(other.toDouble())
operator fun Vec3.div(other: Number) = this.scale(1.0 / other.toDouble())
operator fun Vec3.get(index: Int): Double {
    return when (index) {
        0 -> x
        1 -> y
        2 -> z
        else -> throw IllegalArgumentException("Index must be between 0 and 2")
    }
}
operator fun Vec3.set(index: Int, value: Double): Vec3 {
    val vector = when (index) {
        0 -> Vec3(value, y, z)
        1 -> Vec3(x, value, z)
        2 -> Vec3(x, y, value)
        else -> throw IllegalArgumentException("Index must be between 0 and 2")
    }
    return vector
}

fun AABB.closestPoint(point: Vec3): Vec3 {
    var q = Vec3(0.0, 0.0, 0.0)
    val min = Vec3(minX, minY, minZ)
    val max = Vec3(maxX, maxY, maxZ)
    for (i in 0..2) {
        var v = point[i]
        if (v < min[i]) v = min[i]
        if (v > max[i]) v = max[i]
        q = q.set(i, v)
    }
    return q
}
