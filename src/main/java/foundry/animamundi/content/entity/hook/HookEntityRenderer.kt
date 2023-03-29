package foundry.animamundi.content.entity.hook

import com.mojang.blaze3d.vertex.PoseStack
import com.mojang.blaze3d.vertex.VertexConsumer
import com.mojang.math.Matrix3f
import com.mojang.math.Vector3f
import foundry.animamundi.AnimaMundi
import foundry.animamundi.math.VecUtil
import foundry.animamundi.extensions.minus
import foundry.animamundi.extensions.plus
import foundry.animamundi.extensions.times
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.culling.Frustum
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.Direction
import net.minecraft.resources.ResourceLocation
import net.minecraft.util.Mth
import net.minecraft.world.phys.Vec3
import java.awt.Color

// TODO: replace this whole thing
class HookEntityRenderer(ctx: EntityRendererProvider.Context?) : EntityRenderer<HookEntity>(ctx) {
    override fun render(
        entity: HookEntity, plungerYaw: Float, pt: Float, ps: PoseStack, buffer: MultiBufferSource,
        light: Int
    ) {
        ps.pushPose()

        val entityPosition = entity.getPosition(pt)
        ps.translate(-entityPosition.x, -entityPosition.y, -entityPosition.z)

        val points = entity.points
        val prevPoints = entity.prevPoints

        if(points.size > 1)
            for (i in 0 until (prevPoints.size - 1)) {
                var a = prevPoints[i] + (points[i] - prevPoints[i]) * pt
                var b = prevPoints[i + 1]  + (points[i + 1] - prevPoints[i + 1]) * pt

                renderCuboidLine(ps, buffer, a, b, a.distanceTo(b))
            }

        ps.popPose()
    }

    //#region beam

    protected var transformNormals: Matrix3f? = null

    fun deg(angle: Double): Float {
        return if (angle == 0.0) 0.0f else (angle * 180 / Math.PI).toFloat()
    }

    fun renderCuboidLine(ms: PoseStack, buffer: MultiBufferSource, start: Vec3, end: Vec3, dist: Double) {
        val diff = end.subtract(start)
        val hAngle = deg(Mth.atan2(diff.x, diff.z))
        val hDistance = diff.multiply(1.0, 0.0, 1.0)
            .length().toFloat()
        val vAngle = deg(Mth.atan2(hDistance.toDouble(), diff.y)) - 90
        ms.pushPose()
        ms.translate(start.x, start.y, start.z)
        ms.mulPose(Vector3f.YP.rotationDegrees(hAngle))
        ms.mulPose(Vector3f.XP.rotationDegrees(vAngle))
        renderAACuboidLine(ms, buffer, Vec3.ZERO, Vec3(0.0, 0.0, diff.length()), dist)
        ms.popPose()
    }

    fun renderAACuboidLine(ms: PoseStack, buffer: MultiBufferSource, start: Vec3, end: Vec3, dist: Double) {
        // line width
        var start = start
        var end = end
        var dist = dist
        var lineWidth = 2 / 16.0f
        if (lineWidth == 0f) return
        val r = AnimaMundi.path("textures/entity/archbishop/beam.png")
        var builder = buffer.getBuffer(RenderType.entitySolid(r));

        var diff = end.subtract(start)
        if (diff.x + diff.y + diff.z < 0) {
            val temp = start
            start = end
            end = temp
            diff = diff.scale(-1.0)
        }
        val extension = diff.normalize()
            .scale((lineWidth / 2).toDouble())
        var plane: Vec3 = VecUtil.axisAlingedPlaneOf(diff)
        var face = Direction.getNearest(diff.x, diff.y, diff.z)
        val axis = face.axis
        start = start.subtract(extension)
        end = end.add(extension)
        plane = plane.scale((lineWidth / 2).toDouble())
        val a1 = plane.add(start)
        val b1 = plane.add(end)
        plane = VecUtil.rotate(plane, -90.0, axis)
        val a2 = plane.add(start)
        val b2 = plane.add(end)
        plane = VecUtil.rotate(plane, -90.0, axis)
        val a3 = plane.add(start)
        val b3 = plane.add(end)
        plane = VecUtil.rotate(plane, -90.0, axis)
        val a4 = plane.add(start)
        val b4 = plane.add(end)
        if (false) {
            face = Direction.UP
            putQuad(ms, builder, b4, b3, b2, b1, face, dist)
            putQuad(ms, builder, a1, a2, a3, a4, face, dist)
            putQuad(ms, builder, a1, b1, b2, a2, face, dist)
            putQuad(ms, builder, a2, b2, b3, a3, face, dist)
            putQuad(ms, builder, a3, b3, b4, a4, face, dist)
            putQuad(ms, builder, a4, b4, b1, a1, face, dist)
            return
        }
        putQuad(ms, builder, b4, b3, b2, b1, face, dist)
        putQuad(ms, builder, a1, a2, a3, a4, face.opposite, dist)
        var vec = a1.subtract(a4)
        face = Direction.getNearest(vec.x, vec.y, vec.z)
        putQuad(ms, builder, a1, b1, b2, a2, face, dist)
        vec = VecUtil.rotate(vec, -90.0, axis)
        face = Direction.getNearest(vec.x, vec.y, vec.z)
        putQuad(ms, builder, a2, b2, b3, a3, face, dist)
        vec = VecUtil.rotate(vec, -90.0, axis)
        face = Direction.getNearest(vec.x, vec.y, vec.z)
        putQuad(ms, builder, a3, b3, b4, a4, face, dist)
        vec = VecUtil.rotate(vec, -90.0, axis)
        face = Direction.getNearest(vec.x, vec.y, vec.z)
        putQuad(ms, builder, a4, b4, b1, a1, face, dist)
    }

    fun putQuad(
        ms: PoseStack, builder: VertexConsumer, v1: Vec3, v2: Vec3, v3: Vec3, v4: Vec3,
        normal: Direction?, dist: Double
    ) {
        putQuadUV(ms, builder, v1, v2, v3, v4, 0.0f, 0f, dist.toFloat() * (16 / 23f), 1f, normal)
    }

    fun putQuadUV(
        ms: PoseStack, builder: VertexConsumer, v1: Vec3, v2: Vec3, v3: Vec3, v4: Vec3, minU: Float,
        minV: Float, maxU: Float, maxV: Float, normal: Direction?
    ) {
        putVertex(ms, builder, v1, minU, minV, normal)
        putVertex(ms, builder, v2, maxU, minV, normal)
        putVertex(ms, builder, v3, maxU, maxV, normal)
        putVertex(ms, builder, v4, minU, maxV, normal)
    }

    protected fun putVertex(ms: PoseStack, builder: VertexConsumer, pos: Vec3, u: Float, v: Float, normal: Direction?) {
        val i = 15 shl 20 or (15 shl 4)
        val j = i shr 16 and '\uffff'.code
        val k = i and '\uffff'.code
        val peek = ms.last()
        val rgb = Color.WHITE
        if (transformNormals == null) transformNormals = peek.normal()
        var xOffset = 0
        var yOffset = 0
        var zOffset = 0
        if (normal != null) {
            xOffset = normal.stepX
            yOffset = normal.stepY
            zOffset = normal.stepZ
        }
        builder.vertex(peek.pose(), pos.x.toFloat(), pos.y.toFloat(), pos.z.toFloat())
            .color(rgb.red / 255f, rgb.green / 255f, rgb.blue / 255f, rgb.alpha / 255f * 1.0f)
            .uv(u, v)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(j, k)
            .normal(peek.normal(), xOffset.toFloat(), yOffset.toFloat(), zOffset.toFloat())
            .endVertex()
        transformNormals = null
    }

    fun quad(
        consumer: VertexConsumer,
        stack: PoseStack,
        width: Float,
        height: Float,
        light: Int,
        color: Color,
        alpha: Int
    ) {
        val positions =
            arrayOf(Vector3f(-1f, -1f, 0f), Vector3f(1f, -1f, 0f), Vector3f(1f, 1f, 0f), Vector3f(-1f, 1f, 0f))
        val last = stack.last().pose()
        for (position in positions) {
            position.mul(width, height, width)
        }
        val u0 = 0f
        val u1 = 1f
        val v0 = 0f
        val v1 = 1f
        consumer.vertex(last, positions[0].x(), positions[0].y(), positions[0].z())
            .color(color.red, color.green, color.blue, alpha).uv(u0, v1).overlayCoords(0).uv2(light)
            .normal(0.0f, 1.0f, 0.0f).endVertex()
        consumer.vertex(last, positions[1].x(), positions[1].y(), positions[1].z())
            .color(color.red, color.green, color.blue, alpha).uv(u1, v1).overlayCoords(0).uv2(light)
            .normal(0.0f, 1.0f, 0.0f).endVertex()
        consumer.vertex(last, positions[2].x(), positions[2].y(), positions[2].z())
            .color(color.red, color.green, color.blue, alpha).uv(u1, v0).overlayCoords(0).uv2(light)
            .normal(0.0f, 1.0f, 0.0f).endVertex()
        consumer.vertex(last, positions[3].x(), positions[3].y(), positions[3].z())
            .color(color.red, color.green, color.blue, alpha).uv(u0, v0).overlayCoords(0).uv2(light)
            .normal(0.0f, 1.0f, 0.0f).endVertex()
    }

    //#endregion

    override fun getTextureLocation(pEntity: HookEntity): ResourceLocation? {
        return null
    }

    override fun shouldRender(
        p_225626_1_: HookEntity,
        p_225626_2_: Frustum,
        p_225626_3_: Double,
        p_225626_5_: Double,
        p_225626_7_: Double
    ): Boolean {
        return true
    }
}