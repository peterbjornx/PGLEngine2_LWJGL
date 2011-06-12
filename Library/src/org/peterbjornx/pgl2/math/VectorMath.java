package org.peterbjornx.pgl2.math;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;

import static java.lang.Math.*;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/9/11
 * Time: 12:46 AM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class VectorMath {

    public static Quaternion axisAngleToQuaternion(Vector3f axis, float angle)

    {
        Quaternion quat = new Quaternion(1, 0, 0, 0);
        float sinAngle;
        angle = (float) toRadians(angle);
        angle *= 0.5f;
        Vector3f vn = new Vector3f();
        axis.normalise(vn);
        sinAngle = (float) sin(angle);
        quat.setX(vn.x * sinAngle);
        quat.setY(vn.y * sinAngle);
        quat.setZ(vn.z * sinAngle);
        quat.setW((float) cos(angle));
        return quat;
    }

    public static Quaternion eulerAnglesToQuaternion(Vector3f eulerAngles) {
        Quaternion quat = new Quaternion(1, 0, 0, 0);
        double p = Math.toRadians(eulerAngles.x) / 2.0;
        double y = Math.toRadians(eulerAngles.y) / 2.0;
        double r = Math.toRadians(eulerAngles.z) / 2.0;
        double sinp = sin(p);
        double siny = sin(y);
        double sinr = sin(r);
        double cosp = cos(p);
        double cosy = cos(y);
        double cosr = cos(r);
        quat.setX((float) (sinr * cosp * cosy - cosr * sinp * siny));
        quat.setY((float) (cosr * sinp * cosy + sinr * cosp * siny));
        quat.setZ((float) (cosr * cosp * siny - sinr * sinp * cosy));
        quat.setW((float) (cosr * cosp * cosy + sinr * sinp * siny));
        return quat.normalise(null);
    }

    public static Vector3f quatRotate(Quaternion left, Vector3f right) {
        Quaternion vecQuat = new Quaternion(right.x, right.y, right.z, 0.0f);
        Quaternion resQuat;
        resQuat = Quaternion.mul(vecQuat, left.negate(null), null);
        resQuat = Quaternion.mul(left, resQuat, null);
        return new Vector3f(resQuat.x, resQuat.y, resQuat.z);
    }

    public static Matrix4f matrixForQuaternion(Quaternion quat) {
        float x2 = quat.x * quat.x;
        float y2 = quat.y * quat.y;
        float z2 = quat.z * quat.z;
        float xy = quat.x * quat.y;
        float xz = quat.x * quat.z;
        float yz = quat.y * quat.z;
        float wx = quat.w * quat.x;
        float wy = quat.w * quat.y;
        float wz = quat.w * quat.z;
        return Matrix4fFromNumbers(1.0f - 2.0f * (y2 + z2), 2.0f * (xy - wz), 2.0f * (xz + wy), 0.0f,
                2.0f * (xy + wz), 1.0f - 2.0f * (x2 + z2), 2.0f * (yz - wx), 0.0f,
                2.0f * (xz - wy), 2.0f * (yz + wx), 1.0f - 2.0f * (x2 + y2), 0.0f,
                0.0f, 0.0f, 0.0f, 1.0f);

    }

    private static Matrix4f Matrix4fFromNumbers(float v0, float v1, float v2, float v3, float v4, float v5, float v6, float v7, float v8, float v9, float v10, float v11, float v12, float v13, float v14, float v15) {
        Matrix4f matrix = new Matrix4f();
        matrix.m00 = v0;
        matrix.m01 = v1;
        matrix.m02 = v2;
        matrix.m03 = v3;
        matrix.m10 = v4;
        matrix.m11 = v5;
        matrix.m12 = v6;
        matrix.m13 = v7;
        matrix.m20 = v8;
        matrix.m21 = v9;
        matrix.m22 = v10;
        matrix.m23 = v11;
        matrix.m30 = v12;
        matrix.m31 = v13;
        matrix.m32 = v14;
        matrix.m33 = v15;
        return matrix;
    }

    public static Vector3f mult(Vector3f left, Vector3f right) {
        return new Vector3f(left.x * right.x, left.y * right.y, left.z * right.z);
    }

}
