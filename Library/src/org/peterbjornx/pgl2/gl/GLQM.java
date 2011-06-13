package org.peterbjornx.pgl2.gl;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.math.VectorMath;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.glMultMatrix;
import static org.lwjgl.opengl.GL11.glTranslatef;

/**
 * User: Peter
 * Date: 6/10/11
 * Time: 4:42 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Provides OpenGL wrapper routines for quaternion rotations, Vector3f translations
 * and routines to create buffers from various data types
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class GLQM {

    /**
     * Rotate the current matrix by the given quaternion
     * @param quat The quaternion to rotate the matrix by.
     */
    public static void glRotateq(Quaternion quat){
        FloatBuffer transformBuffer = BufferUtils.createFloatBuffer(16);
        Matrix4f rotationMatrix = VectorMath.matrixForQuaternion(quat);
        rotationMatrix.store(transformBuffer);
        transformBuffer.flip();
        glMultMatrix(transformBuffer);
    }

    /**
     * Translates the current matrix by the given vector
     * @param offset The vector describing the translation.
     */
    public static void glTranslatevf(Vector3f offset){
        glTranslatef(offset.x,offset.y,offset.z);
    }

    /**
     * Stores a Colour4f in a FloatBuffer
     * @param colour The colour to store
     * @return The FloatBuffer containing the colour
     */
    public static FloatBuffer getColour4fBuffer(Color colour){
        FloatBuffer colourBuffer = BufferUtils.createFloatBuffer(4);
        colourBuffer.put(colour.getRed() / 255.0f);
        colourBuffer.put(colour.getGreen() / 255.0f);
        colourBuffer.put(colour.getBlue() / 255.0f);
        colourBuffer.put(colour.getAlpha() / 255.0f);
        colourBuffer.rewind();
        return colourBuffer;
    }

    /**
     * Stores a Vector3f in a FloatBuffer
     * @param vector The vector to store
     * @return The FloatBuffer containing the vector
     */
    public static FloatBuffer getVector3fBuffer(Vector3f vector){
        FloatBuffer vectorBuffer = BufferUtils.createFloatBuffer(4);
        vectorBuffer.put(vector.getX());
        vectorBuffer.put(vector.getY());
        vectorBuffer.put(vector.getZ());
        vectorBuffer.rewind();
        return vectorBuffer;
    }

    /**
     * Stores a Vector3f and a seperate float representing w in a FloatBuffer
     * @param vector The vector to store
     * @param w The w component of the vector
     * @return The FloatBuffer containing the vector
     */
    public static FloatBuffer getVector4fBuffer(Vector3f vector,float w){
        FloatBuffer vectorBuffer = BufferUtils.createFloatBuffer(4);
        vectorBuffer.put(vector.getX());
        vectorBuffer.put(vector.getY());
        vectorBuffer.put(vector.getZ());
        vectorBuffer.put(w);
        vectorBuffer.rewind();
        return vectorBuffer;
    }

}
