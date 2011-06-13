package org.peterbjornx.pgl2.buffer;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.util.PglException;

/**
 * User: Peter
 * Date: 6/12/11
 * Time: 9:07 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Describers the interface provided for geometry buffers
 * @author Peter Bosch (AKA Peterbjorn)
 */
public interface GeometryBuffer extends OpenGLBuffer{
    /**
     * Adds a vertex to the geometry buffer with the given values
     * @param pos The position of this vertex
     * @param normal The normal vector for this vector
     * @param texcoord The texture coordinates for this vector
     * @param colour This vector's colour
     * @throws PglException When an attempt is made to write to a geometry buffer already uploaded to the server
     */
    void addVertex(Vector3f pos,Vector3f normal,Vector3f texcoord, Color colour) throws PglException;

    /**
     * Gets the amount of vertices in the buffer
     * @return The amount of vertices in the buffer
     */
    int getVertexCount();
}
