package foundry.animamundi.math;

import com.mojang.math.Quaternion;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

// TODO: replace with non-aero non-create stuff
public class VecUtil {

    public static Vec3 rotate(Vec3 vec, Vec3 rotationVec) {
        return rotate(vec, rotationVec.x, rotationVec.y, rotationVec.z);
    }

    public static Vec3 rotate(Vec3 vec, double xRot, double yRot, double zRot) {
        return rotate(rotate(rotate(vec, xRot, Direction.Axis.X), yRot, Direction.Axis.Y), zRot, Direction.Axis.Z);
    }


    public static Vec3 rotate(Vec3 vec, double deg, Direction.Axis axis) {
        if (deg == 0.0D) {
            return vec;
        } else if (vec == Vec3.ZERO) {
            return vec;
        } else {
            float angle = (float)(deg / 180.0D * 3.141592653589793D);
            double sin = (double)Mth.sin(angle);
            double cos = (double) Mth.cos(angle);
            double x = vec.x;
            double y = vec.y;
            double z = vec.z;
            if (axis == Direction.Axis.X) {
                return new Vec3(x, y * cos - z * sin, z * cos + y * sin);
            } else if (axis == Direction.Axis.Y) {
                return new Vec3(x * cos + z * sin, y, z * cos - x * sin);
            } else {
                return axis == Direction.Axis.Z ? new Vec3(x * cos - y * sin, y * cos + x * sin, z) : vec;
            }
        }
    }


    public static Vec3 axisAlingedPlaneOf(Direction face) {
        return axisAlingedPlaneOf(Vec3.atLowerCornerOf(face.getNormal()));
    }


    public static Vec3 axisAlingedPlaneOf(Vec3 vec) {
        vec = vec.normalize();
        return (new Vec3(1.0D, 1.0D, 1.0D)).subtract(Math.abs(vec.x), Math.abs(vec.y), Math.abs(vec.z));
    }

    /**
     * Rotates a vector by a quaternion
     *
     * @param V The vector to be rotated
     * @param Q The quaternion to rotate by
     * @return The rotated vector
     */
    public static Vec3 rotateQuat(Vec3 V, Quaternion Q)
    {
        Quaternion q=new Quaternion((float)V.x,(float)V.y,(float)V.z,0.0f);
        Quaternion Q2 = Q.copy();
        q.mul(Q2);
        Q2.conj();
        Q2.mul(q);
        return new Vec3(Q2.i(),Q2.j(),Q2.k());
    }
    /**
     * Rotates a vector by the inverse of a quaternion
     *
     * @param V The vector to be rotated
     * @param Q The quaternion to rotate by
     * @return The rotated vector
     */
    public static Vec3 rotateQuatReverse(Vec3 V, Quaternion Q)
    {
        Quaternion q=new Quaternion((float)V.x,(float)V.y,(float)V.z,0.0f);
        Quaternion Q2 = Q.copy();
        Q2.conj();
        q.mul(Q2);
        Q2.conj();
        Q2.mul(q);
        return new Vec3(Q2.i(),Q2.j(),Q2.k());
    }



}
