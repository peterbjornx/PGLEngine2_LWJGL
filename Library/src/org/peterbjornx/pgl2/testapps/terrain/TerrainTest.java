package org.peterbjornx.pgl2.testapps.terrain;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.input.cameracontrol.FirstPersonCamera;
import org.peterbjornx.pgl2.terrain.Terrain;
import org.peterbjornx.pgl2.terrain.TerrainSource;
import org.peterbjornx.pgl2.texture.Texture2D;
import org.peterbjornx.pgl2.util.SimpleApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.font.GlyphJustificationInfo;
import java.awt.image.BufferedImage;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/16/11
 * Time: 6:59 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class TerrainTest extends SimpleApplication implements TerrainSource{
    private FirstPersonCamera fpsCamera = new FirstPersonCamera();
    private Terrain terrain;
    /**
     * This method is called when the application is launched
     */
    @Override
    protected void init() {
        initializePGLEngine(1200, 600);
        terrain = new Terrain(this);
        scene.add(terrain);
        terrain.setPosition(new Vector3f(1,0,1));
    }

    /**
     * This method is called every frame before rendering starts
     */
    @Override
    protected void preRender() {
        fpsCamera.handleInput(camera);
    }

    public int[][] getHeightMap() {
        return new int[][]{
            {0,0,0,0,0},
            {0,1,1,1,0},
            {0,1,2,1,0},
            {0,1,1,1,0},
            {0,0,0,0,0}
        };
    }

    public Color[][] getColorMap() {
        Color grass = new Color(53,163,77,255);
        Color sand = new Color(255,249,13,255);
        Color rock = new Color(128,128,128,255);
        return new Color[][]{
            {grass,grass,grass,grass,grass},
            {grass,sand,sand,sand,grass},
            {grass,sand,rock,sand,grass},
            {grass,sand,sand,sand,grass},
            {grass,grass,grass,grass,grass},
        };
    }

    public int[][] getTextureMap() {
        return new int[][]{
            {0,0,0,0,0},
            {0,1,1,1,0},
            {0,1,2,1,0},
            {0,1,1,1,0},
            {0,0,0,0,0}
        };
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see Determining If an Image Has Transparent Pixels
        boolean hasAlpha = false;

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics g = bimage.createGraphics();

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }


    public Texture2D getTexture(int id) {
        // Copy image to buffered image
        return new Texture2D("./tex/" + id + ".png");
    }

    public int getTileSize() {
        return 1;
    }

    public int getSideSize() {
        return 5;
    }

    public static void main(String[] args) {
        new TerrainTest().main();
    }
}
