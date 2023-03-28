package foundry.animamundi.math;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.world.phys.Vec3;

public class MathUtils {

    /**
     * Clamps a normalized vector inside a cone, giving a maximum angle between the returned vector
     * and the axis vector of the cone
     *
     * @param v Vector to be clamped
     * @param coneAxis Central axis of the cone
     * @param coneAngle Maximum angle in radians between the axis vector and the output vector
     * @return Clamped vector
     */
    public static Vec3 clampIntoCone(Vec3 v,Vec3 coneAxis,double coneAngle)
    {
        double vv = v.dot(v);
        double vn = v.dot(coneAxis);
        double nn = coneAxis.dot(coneAxis);
        //the 1.01 is to prevent floating point issues when v=axis,
        //and also have it behave smoother when v is almost the opposite of axis
        double disc = nn*vv*1.01 - vn*vn;
        //quadratic formula
        double offsetDistance = (-vn + Math.sqrt(disc)/Math.tan(coneAngle))/nn;
        if(offsetDistance<0 ^ coneAngle<0)
            return v;

        return (v.add(coneAxis.scale(offsetDistance))).normalize();
    }

    /**
     * Constructs a quaternion that will rotate a given start vector to an end vector along the shortest possible path
     * @param start The start vector the rotation begins at
     * @param end The end vector the rotation should end at
     * @return A rotation quaternion from vector start to end
     */
    public static Quaternion getQuaternionromVectorRotation(Vec3 start, Vec3 end)
    {
        Vector3f cross = new Vector3f(start.cross(end));
        Quaternion Q = new Quaternion(cross.x(),cross.y(),cross.z(),1.0f+(float)start.dot(end));
        Q.normalize();
        return Q;
    }

    /**
     * Tests if a vector is inside a cylinder
     *
     * @param axisVector Central axis of the cylinder, must be normalized
     * @param relativePosition Vector to be tested, relative to the base of the cylinder
     * @param cylinderLength Length of the cylinder
     * @param cylinderRadius Radius of the cylinder
     * @return If the check passed
     */
    public static boolean isInCylinder(Vec3 axisVector, Vec3 relativePosition, double cylinderLength, double cylinderRadius) {
        double distance = axisVector.dot(relativePosition);
        if (distance < 0 || distance > cylinderLength)
            return false;

        relativePosition = relativePosition.subtract(axisVector.scale(distance));
        return relativePosition.lengthSqr() <= cylinderRadius * cylinderRadius;
    }

    /**
     * Obtains the approximate euler angles from a quaternion
     * Avoid use if possible
     * @param q The quaternion to obtain the angles from
     * @return The angles in radians
     */
    public static Vec3 getEuler(Quaternion q) {
        double piO2 = (Math.PI / 2);

        double w = q.r(), x = q.i(), y = q.j(), z = q.k();

        double rollX = 2 * (w * x + y * z);
        double rollY = 1 - 2 * (x * x + y * y);
        double roll = Math.atan2(rollX, rollY);

        double pitch = 0;
        double pitchV = 2 * (w * y - z * x);
        if (Math.abs(pitchV) >= 1)
            pitch = Math.copySign(piO2, pitchV);
        else
            pitch = Math.asin(pitchV);

        double yawX = 2 * (w * z + x * y);
        double yawY = 1 - 2 * (y * y + z * z);
        double yaw = Math.atan2(yawX, yawY);

        return new Vec3(roll, pitch, yaw);
    }

    /**
     * Clamp a quaternion to a given axis
     * @param Q The quaternion to clamp
     * @param v The axis to clamp to
     * @return The clamped quaternion
     */
    public static Quaternion clampToRotationVector(Quaternion Q, Vec3 v)
    {
        Vec3 v0 = new Vec3(Q.i(),Q.j(),Q.k());
        double D = v0.dot(v);
        double den = Math.sqrt(D*D + Q.r()*Q.r());
        double real = Q.r()/den;
        double imag = D/den;
        Vec3 f = v.scale(imag);
        return new Quaternion((float) f.x, (float) f.y, (float) f.z, (float) real);

    }


}
