package org.peterbjornx.pgl2.buffer.impl;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBVertexBufferObject.*;
import static org.peterbjornx.pgl2.util.PortabilityUtils.putFloatLE;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.buffer.GeometryBuffer;
import org.peterbjornx.pgl2.util.PglException;
import org.peterbjornx.pgl2.util.ServerMemoryManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 1:48 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class ArbGeometryBuffer implements GeometryBuffer{
    private ByteBuffer vertexBuffer;
    private boolean use3DTextures;
    private int vboPtr;
    private int vertexCount = 0;
    private boolean dataTransferred = false;
    private int serverMemoryUsage = 0;

    public ArbGeometryBuffer(int maxSize,boolean use3DTextures) throws PglException {
        if (!GLContext.getCapabilities().GL_ARB_vertex_buffer_object)
            throw new PglException("Card does not support ARB VBOs");
        this.use3DTextures = use3DTextures;
        vertexBuffer = BufferUtils.createByteBuffer(maxSize*(use3DTextures?52:48));
        vboPtr = glGenBuffersARB();
    }

    public void addVertex(Vector3f pos, Vector3f normal, Vector3f texCoord, Color colour) throws PglException {
        if (dataTransferred)
            throw new PglException("Tried to modify uploaded VBO");
        if (ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN){
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
            vertexBuffer.putFloat(colour.getRed()/255.0f);
            vertexBuffer.putFloat(colour.getGreen()/255.0f);
            vertexBuffer.putFloat(colour.getBlue()/255.0f);//48 36
            vertexBuffer.putFloat(colour.getAlpha()/255.0f);//52 40
            vertexCount++;
        } else {
            putFloatLE(vertexBuffer, pos.getX());
            putFloatLE(vertexBuffer, pos.getY());
            putFloatLE(vertexBuffer, pos.getZ()); //12 0
            putFloatLE(vertexBuffer, normal.getX());
            putFloatLE(vertexBuffer, normal.getY());
            putFloatLE(vertexBuffer, normal.getZ());//24 12
            putFloatLE(vertexBuffer, texCoord.getX());//28 16
            putFloatLE(vertexBuffer, texCoord.getY());//32 20
            if (use3DTextures)
                putFloatLE(vertexBuffer, texCoord.getZ());//36 24
            putFloatLE(vertexBuffer, colour.getRed() / 255.0f);//48 36
            putFloatLE(vertexBuffer, colour.getGreen() / 255.0f); //RED
            putFloatLE(vertexBuffer, colour.getBlue() / 255.0f);
            putFloatLE(vertexBuffer, colour.getAlpha() / 255.0f);//52 40
            vertexCount++;
        }
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void bind() {
        glBindBufferARB(GL_ARRAY_BUFFER_ARB,vboPtr);
        if(!dataTransferred){
            dataTransferred = true;
            vertexBuffer.flip();
            serverMemoryUsage = vertexBuffer.limit();
            glBufferDataARB(GL_ARRAY_BUFFER_ARB,vertexBuffer,GL_STATIC_DRAW_ARB);
            ServerMemoryManager.arbBufferMemory += serverMemoryUsage;
            vertexBuffer = null;
        }
        glVertexPointer(3, GL_FLOAT, use3DTextures ? 52 : 48, 0);
        glNormalPointer(GL_FLOAT, use3DTextures ? 52 : 48, 12);
        glTexCoordPointer(use3DTextures ? 3 : 2, GL_FLOAT, use3DTextures ? 52 : 48, 24);
        glColorPointer(4, GL_FLOAT, use3DTextures ? 52 : 48, use3DTextures ? 36 : 32);
    }

    public void enable() {
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_NORMAL_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
    }

    public void disable() {
        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_NORMAL_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_COLOR_ARRAY);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        vertexBuffer = null;
        ServerMemoryManager.requestARBBufferDeletion(vboPtr,serverMemoryUsage);
    }
}
