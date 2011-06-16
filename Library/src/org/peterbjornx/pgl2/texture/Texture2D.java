package org.peterbjornx.pgl2.texture;

import org.lwjgl.opengl.GL11;
import org.peterbjornx.pgl2.internal.GLAttribute;
import org.peterbjornx.pgl2.util.ServerMemoryManager;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.LinkedList;

/**
 * User: Peter
 * Date: 6/9/11
 * Time: 7:58 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Provides an easy interface to OpenGL 2D Textures
 * @author Peter Bosch (AKA Peterbjorn)
 */



public class Texture2D {

    private int texturePtr;
    private boolean imageLoaded = false;
    private int width;
    private int height;
    private int format;
    private int bufFormat;
    private ByteBuffer pixels;
    private int serverMemoryUsage = 0;

    public Texture2D(int format, BufferedImage image) {
        this.format = format;
        width = image.getWidth();
        height = image.getHeight();
        int array[] = new int[width*height];
        image.getRGB(0,0,width,height, array,0,width);
        for (int texel:array)
            pixels.put(new byte[]{(byte)(texel >> 16),(byte)(texel >> 8),(byte)texel},0,3);
        texturePtr = glGenTextures();
        glTexParameteri(GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    }

    public Texture2D(int width, int height, int format, int bufFormat, ByteBuffer pixels) {
        this.width = width;
        this.height = height;
        this.format = format;
        this.bufFormat = bufFormat;
        this.pixels = pixels;
        texturePtr = glGenTextures();
        glTexParameteri(GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    }

    public void glTexParameteri(int id,int value){
        bind();
        GL11.glTexParameteri(GL_TEXTURE_2D, id, value);
    }

    public void glTexParameterf(int id,float value){
        bind();
        GL11.glTexParameterf(GL_TEXTURE_2D, id, value);
    }

    public void enable(){
        glEnable(GL_TEXTURE_2D);
    }

    public void disable(){
        glDisable(GL_TEXTURE_2D);
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D,texturePtr);
        if (!imageLoaded){
            glTexImage2D(GL_TEXTURE_2D,0,format,width,height,0,bufFormat,GL_UNSIGNED_BYTE,pixels);
            serverMemoryUsage+=pixels.limit();
            pixels=null;
            ServerMemoryManager.textureMemory+=serverMemoryUsage;
            imageLoaded = true;
        }
    }


    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        pixels = null;
        ServerMemoryManager.requestTextureDeletion(texturePtr, serverMemoryUsage);
    }
}
