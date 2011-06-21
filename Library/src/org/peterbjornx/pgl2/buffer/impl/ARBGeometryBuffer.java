package org.peterbjornx.pgl2.buffer.impl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.buffer.GeometryBuffer;
import org.peterbjornx.pgl2.util.PglException;
import org.peterbjornx.pgl2.util.ServerMemoryManager;
import org.peterbjornx.pgl2.util.memory.DirectBufferManager;

import java.nio.ByteBuffer;

/**
 * User: Peter
 * Date: 6/13/11
 * Time: 1:48 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * ARB VBO geometry buffer implementation
 *
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class ARBGeometryBuffer implements GeometryBuffer {
    private ByteBuffer vertexBuffer;
    private boolean use3DTextures;
    private int vboPtr;
    private int vertexCount = 0;
    private boolean dataTransferred = false;
    private int serverMemoryUsage = 0;

    /**
     * Creates a new ARB geometry buffer
     *
     * @param maxSize       Maximal amount of vertices this buffer may store
     * @param use3DTextures If the W texture coordinate should be enabled
     * @throws PglException When the card does not support ARB VBOs
     */
    public ARBGeometryBuffer(int maxSize, boolean use3DTextures) throws PglException {
        if (!GLContext.getCapabilities().GL_ARB_vertex_buffer_object)
            throw new PglException("Card does not support ARB VBOs");
        this.use3DTextures = use3DTextures;
        vertexBuffer = DirectBufferManager.alloc(maxSize * (use3DTextures ? 52 : 48));
        vboPtr = glGenBuffersARB();
    }

    /**
     * Adds a vertex to the geometry buffer with the given values
     *
     * @param pos      The position of this vertex
     * @param normal   The normal vector for this vector
     * @param texCoord The texture coordinates for this vector
     * @param colour   This vector's colour
     * @throws PglException When an attempt is made to write to a geometry buffer already uploaded to the server
     */
    public int addVertex(Vector3f pos, Vector3f normal, Vector3f texCoord, Color colour) throws PglException {
        if (dataTransferred)
            throw new PglException("Tried to modify uploaded VBO");
        vertexBuffer.putFloat(pos.getX());
        vertexBuffer.putFloat(pos.getY());
        vertexBuffer.putFloat(pos.getZ()); //12 0
        vertexBuffer.putFloat(normal.getX());
        vertexBuffer.putFloat(normal.getY());
        vertexBuffer.putFloat(normal.getZ());//24 12
        vertexBuffer.putFloat(texCoord.getX());//28 16
        vertexBuffer.putFloat(texCoord.getY());//32 20
        if (use3DTextures)
            vertexBuffer.putFloat(texCoord.getZ());//36 24
        vertexBuffer.putFloat(colour.getRed() / 255.0f);
        vertexBuffer.putFloat(colour.getGreen() / 255.0f);
        vertexBuffer.putFloat(colour.getBlue() / 255.0f);//48 36
        vertexBuffer.putFloat(colour.getAlpha() / 255.0f);//52 40
        vertexCount++;
        return vertexCount - 1;
    }

    /**
     * Gets the amount of vertices in the buffer
     *
     * @return The amount of vertices in the buffer
     */
    public int getVertexCount() {
        return vertexCount;
    }

    /**
     * Activates this buffer
     */
    public void bind() {
        glBindBufferARB(GL_ARRAY_BUFFER_ARB, vboPtr);
        if (!dataTransferred) {
            dataTransferred = true;
            vertexBuffer.flip();
            serverMemoryUsage = vertexBuffer.limit();
            glBufferDataARB(GL_ARRAY_BUFFER_ARB, vertexBuffer, GL_STATIC_DRAW_ARB);
            ServerMemoryManager.arbBufferMemory += serverMemoryUsage;
            DirectBufferManager.free(vertexBuffer);
            vertexBuffer = null;
        }
        glVertexPointer(3, GL_FLOAT, use3DTextures ? 52 : 48, 0);
        glNormalPointer(GL_FLOAT, use3DTextures ? 52 : 48, 12);
        glTexCoordPointer(use3DTextures ? 3 : 2, GL_FLOAT, use3DTextures ? 52 : 48, 24);
        glColorPointer(4, GL_FLOAT, use3DTextures ? 52 : 48, use3DTextures ? 36 : 32);
    }

    /**
     * Enables this buffer's capabilities
     */
    public void enable() {
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
    }

    /**
     * Disables this buffer's capabilities
     */
    public void disable() {
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (vertexBuffer != null)
            DirectBufferManager.free(vertexBuffer);
        vertexBuffer = null;
        ServerMemoryManager.requestARBBufferDeletion(vboPtr, serverMemoryUsage);
    }
}
