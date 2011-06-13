package org.peterbjornx.pgl2.util;

import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;
import java.util.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.ARBBufferObject.*;
/**
 * User: Peter
 * Date: 6/12/11
 * Time: 10:19 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 *
 * Keeps track of server (GPU or in case of software GL CPU) memory and executes off-thread deletion requests
 *
 */
public class ServerMemoryManager {
    /**
     * Amount of memory (in bytes) used for ARB buffer objects
     */
    public static int arbBufferMemory = 0;
    /**
     * Amount of memory (in bytes) used for textures
     */
    public static int textureMemory = 0;
    /**
     * Amount of memory (in bytes) used for GUI textures
     */
    public static int imageMemory = 0;

    private static List<DeletionRequest> arbBufferDeleteQueue = new LinkedList<DeletionRequest>();
    private static List<DeletionRequest> textureDeleteQueue = new LinkedList<DeletionRequest>();
    private static List<DeletionRequest> imageDeleteQueue = new LinkedList<DeletionRequest>();

    private static int[] glObjectIdBuffer = new int[1000];//Tweak this for performance

    private static IntBuffer createIntBufferFromArray(int[] array,int offset,int length){
        IntBuffer buffer = BufferUtils.createIntBuffer(length);
        buffer.put(array,offset,length);
        return buffer;
    }

    /**
     * Executes deletion requests<BR/>
     * Note: Only call from game loop
     */
    public static void processQueues(){
        int bufferPtr = 0;
        if (!arbBufferDeleteQueue.isEmpty()){
            while (!arbBufferDeleteQueue.isEmpty()){
                DeletionRequest d = arbBufferDeleteQueue.remove(0);
                glObjectIdBuffer[bufferPtr++] = d.id;
                arbBufferMemory -= d.size;
                if (bufferPtr == glObjectIdBuffer.length){
                    glDeleteBuffersARB(createIntBufferFromArray(glObjectIdBuffer,0,bufferPtr));
                    bufferPtr = 0;
                }
            }
            if (bufferPtr != 0){
                glDeleteBuffersARB(createIntBufferFromArray(glObjectIdBuffer,0,bufferPtr));
                bufferPtr = 0;
            }
        }
        if (!textureDeleteQueue.isEmpty()){
            while (!textureDeleteQueue.isEmpty()){
                DeletionRequest d = textureDeleteQueue.remove(0);
                glObjectIdBuffer[bufferPtr++] = d.id;
                textureMemory -= d.size;
                if (bufferPtr == glObjectIdBuffer.length){
                    glDeleteTextures(createIntBufferFromArray(glObjectIdBuffer,0,bufferPtr));
                    bufferPtr = 0;
                }
            }
            if (bufferPtr != 0){
                glDeleteTextures(createIntBufferFromArray(glObjectIdBuffer,0,bufferPtr));
                bufferPtr = 0;
            }
        }
        if (!imageDeleteQueue.isEmpty()){
            while (!imageDeleteQueue.isEmpty()){
                DeletionRequest d = imageDeleteQueue.remove(0);
                glObjectIdBuffer[bufferPtr++] = d.id;
                imageMemory -= d.size;
                if (bufferPtr == glObjectIdBuffer.length){
                    glDeleteTextures(createIntBufferFromArray(glObjectIdBuffer, 0, bufferPtr));
                    bufferPtr = 0;
                }
            }
            if (bufferPtr != 0){
                glDeleteTextures(createIntBufferFromArray(glObjectIdBuffer,0,bufferPtr));
            }
        }

    }

    /**
     * Queues an ARB VBO for deletion from server memory.
     * @param glId OpenGL ID for the VBO.
     * @param serverSize VBO size in server memory
     */
    public static void requestARBBufferDeletion(int glId, int serverSize){
        arbBufferDeleteQueue.add(new DeletionRequest(glId,serverSize));
    }

    /**
     * Queues a texture for deletion from server memory.
     * @param glId OpenGL ID for the texture.
     * @param serverSize Texture size in server memory
     */
    public static void requestTextureDeletion(int glId, int serverSize){
        textureDeleteQueue.add(new DeletionRequest(glId,serverSize));
    }

    /**
     * Queues a GUI texture for deletion from server memory.
     * @param glId OpenGL ID for the GUI texture.
     * @param serverSize GUI texture size in server memory
     */
    public static void requestImageDeletion(int glId, int serverSize){
        imageDeleteQueue.add(new DeletionRequest(glId,serverSize));
    }

    private static class DeletionRequest {
        public int id;
        public int size;

        public DeletionRequest(int id,int size){
            this.id = id;
            this.size = size;
        }
    }

}
