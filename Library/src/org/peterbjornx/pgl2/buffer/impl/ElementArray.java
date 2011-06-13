package org.peterbjornx.pgl2.buffer.impl;

import org.lwjgl.opengl.GLContext;
import org.peterbjornx.pgl2.buffer.ElementBuffer;
import org.peterbjornx.pgl2.util.PglException;
import org.peterbjornx.pgl2.util.ServerMemoryManager;

import java.nio.ByteBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 5:25 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class ElementArray implements ElementBuffer{
    private ByteBuffer elementBuffer;
    private boolean dataTransferred = false;
    private int drawMode;
    private int polygonCount = 0;

    /**
     * Creates a new element array
     * @param maxSize Maximal amount of polygons this buffer may store
     * @param drawMode The OpenGL drawing mode to use
     */
    public ElementArray(int maxSize,int drawMode) throws PglException {
        this.elementBuffer = ByteBuffer.allocateDirect(maxSize*sizeof()*4);
        this.drawMode = drawMode;
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
        if (!dataTransferred){
            dataTransferred = true;
            elementBuffer.flip();
        }
    }

    /**
     * Enables this buffer's capabilities
     */
    public void enable() {
    }

    /**
     * Disables this buffer's capabilities
     */
    public void disable() {
    }

    /**
     * Renders the contents of this element buffer.
     * Note: requires that the element and geometry buffers have been bound and enabled
     */
    public void draw(){
        glDrawElements(drawMode, elementBuffer.asIntBuffer());
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
}
