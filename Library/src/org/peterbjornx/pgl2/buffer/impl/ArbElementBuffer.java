package org.peterbjornx.pgl2.buffer.impl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;

import org.lwjgl.opengl.GLContext;
import org.peterbjornx.pgl2.buffer.ElementBuffer;
import org.peterbjornx.pgl2.util.PglException;
import org.peterbjornx.pgl2.util.PortabilityUtils;
import org.peterbjornx.pgl2.util.ServerMemoryManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 2:29 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class ArbElementBuffer implements ElementBuffer {
    private ByteBuffer elementBuffer;
    private int vboPtr;
    private boolean dataTransferred = false;
    private int drawMode;
    private int polygonCount = 0;
    private int serverMemoryUsage = 0;

    public ArbElementBuffer(int maxSize,int drawMode) throws PglException {
        if (!GLContext.getCapabilities().GL_ARB_vertex_buffer_object)
            throw new PglException("Card does not support ARB VBOs");
        this.elementBuffer = ByteBuffer.allocateDirect(maxSize*sizeof()*4);
        vboPtr = glGenBuffersARB();
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

    public void enable() {
        glEnableClientState(GL_ELEMENT_ARRAY_BUFFER_ARB);
    }

    public void disable() {
        glDisableClientState(GL_ELEMENT_ARRAY_BUFFER_ARB);
    }

    public void draw(){
        glDrawElements(drawMode, sizeof()*polygonCount, GL_UNSIGNED_INT, 0);
    }

    public void addPolygon(List<Integer> points) throws PglException {
        if (dataTransferred)
            throw new PglException("Tried to modify uploaded element buffer");
        if (points.size() != sizeof())
            throw new PglException("Tried to add polygon of different element count as draw mode");
        polygonCount++;
        while(true){
            if (points.isEmpty())
                return;
            if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN)
                elementBuffer.putInt(points.remove(0));
            else
                PortabilityUtils.putIntLE(elementBuffer, points.remove(0));
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        elementBuffer = null;
        ServerMemoryManager.requestARBBufferDeletion(vboPtr, serverMemoryUsage);
    }
}
