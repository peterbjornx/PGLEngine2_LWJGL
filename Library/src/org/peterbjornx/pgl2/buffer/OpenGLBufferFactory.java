package org.peterbjornx.pgl2.buffer;

import org.lwjgl.opengl.GLContext;
import org.peterbjornx.pgl2.buffer.impl.ARBGeometryBuffer;
import org.peterbjornx.pgl2.buffer.impl.ArbElementBuffer;
import org.peterbjornx.pgl2.buffer.impl.ElementArray;
import org.peterbjornx.pgl2.buffer.impl.GeometryArray;
import org.peterbjornx.pgl2.util.PglException;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 7:12 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class OpenGLBufferFactory {

    /**
     * Creates a new element buffer
     * @param maxPolygons Maximal amount of polygons this buffer may store
     * @param drawType The OpenGL drawing mode to use
     * @return A compatible buffer
     */
    public static ElementBuffer createElementBuffer(int maxPolygons,int drawType){
        if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object)
            try {
                 return new ArbElementBuffer(maxPolygons,drawType);
            } catch (PglException ignored) {} //Unless caps can suddenly change i dont see this happening.
        else
            return new ElementArray(maxPolygons,drawType);
        return null;
    }

    /**
     * Creates a new vertex array geometry buffer
     * @param maxVertices Maximal amount of vertices this buffer may store
     * @param use3DTextures If the W texture coordinate should be enabled
     * @return A compatible buffer
     */
    public static GeometryBuffer createGeometryBuffer(int maxVertices,boolean use3DTextures){
        if (GLContext.getCapabilities().GL_ARB_vertex_buffer_object)
            try {
                return new ARBGeometryBuffer(maxVertices,use3DTextures);
            } catch (PglException ignored) {} //Unless caps can suddenly change i dont see this happening.
        else
            return new GeometryArray(maxVertices,use3DTextures);
        return null;
    }
}
