package foundry.animamundi.math

import net.minecraft.world.phys.Vec3

data class Manifold(val normal: Vec3, val depth: Double, val contactPointA: Vec3, val contactPointB: Vec3)