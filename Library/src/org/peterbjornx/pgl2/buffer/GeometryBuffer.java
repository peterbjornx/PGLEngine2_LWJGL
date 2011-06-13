package org.peterbjornx.pgl2.buffer;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.util.PglException;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/12/11
 * Time: 9:07 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public interface GeometryBuffer extends OpenGLBuffer{
    void addVertex(Vector3f pos,Vector3f normal,Vector3f texcoord, Color colour) throws PglException;
    int getVertexCount();
}
