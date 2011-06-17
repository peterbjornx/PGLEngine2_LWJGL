package org.peterbjornx.pgl2.buffer.impl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;

import org.lwjgl.opengl.GLContext;
import org.peterbjornx.pgl2.buffer.ElementBuffer;
import org.peterbjornx.pgl2.util.PglException;
import org.peterbjornx.pgl2.util.ServerMemoryManager;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * User: Peter
 * Date: 6/13/11
 * Time: 2:29 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Element buffer implementation that uses ARB VBO's
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class ArbElementBuffer implements ElementBuffer {
    private ByteBuffer elementBuffer;
    private int vboPtr;
    private boolean dataTransferred = false;
    private int drawMode;
    private int polygonCount = 0;
    private int serverMemoryUsage = 0;

    /**
     * Creates a new ARB element buffer
     * @param maxSize Maximal amount of polygons this buffer may store
     * @param drawMode The OpenGL drawing mode to use
     * @throws PglException When the card does not support ARB VBOs
     */
    public ArbElementBuffer(int maxSize,int drawMode) throws PglException {
        if (!GLContext.getCapabilities().GL_ARB_vertex_buffer_object)
            throw new PglException("Card does not support ARB VBOs");
        this.drawMode = drawMode;
        this.elementBuffer = ByteBuffer.allocateDirect(maxSize*sizeof()*4);
        vboPtr = glGenBuffersARB();
    }

    private int sizeof() {
        switch (drawMode){
            case GL_POINTS:
                return 1;
            case GL_LINES:
                return 2;
            case GL_TRIANGLES:
                return 3;
            case GL_QUADS:
                return 4;
        }
        return 1;
    }

    /**
     * Activates this buffer
     */
    public void bind() {
       glBindBufferARB(GL_ELEMENT_ARRAY_BUFFER_ARB, vboPtr);
        if (!dataTransferred){
            dataTransferred = true;
            elementBuffer.flip();
            serverMemoryUsage = elementBuffer.limit();
            glBufferDataARB(GL_ELEMENT_ARRAY_BUFFER_ARB, elementBuffer, GL_STATIC_DRAW_ARB);
            ServerMemoryManager.arbBufferMemory += serverMemoryUsage;
            elementBuffer = null;
        }
    }

    /**
     * Enables this buffer's capabilities
     */
    public void enable() {}

    /**
     * Disables this buffer's capabilities
     */
    public void disable() {}

    /**
     * Renders the contents of this element buffer.
     * Note: requires that the element and geometry buffers have been bound and enabled
     */
    public void draw(){
        glDrawElements(drawMode, sizeof()*polygonCount, GL_UNSIGNED_INT, 0);
    }

    /**
     * Add an polygon (element) to this buffer
     * @param points The vertex indices describing this polygon
     * @throws PglException When this buffer is already uploaded<BR/> to the server or when the polygon index count doesn't match current drawing mode
     */
    public void addPolygon(List<Integer> points) throws PglException {
        if (dataTransferred)
            throw new PglException("Tried to modify uploaded element buffer");
        if (points.size() != sizeof())
            throw new PglException("Tried to add polygon of different element count as draw mode");
        polygonCount++;
        while(true){
            if (points.isEmpty())
                return;
            elementBuffer.putInt(points.remove(0));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        elementBuffer = null;
        ServerMemoryManager.requestARBBufferDeletion(vboPtr, serverMemoryUsage);
    }
}
