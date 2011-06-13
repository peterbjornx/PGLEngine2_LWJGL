package org.peterbjornx.pgl2.util;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.model.Node;

/**
 * User: Peter
 * Date: 6/10/11
 * Time: 12:18 AM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Provides a easy way to create a PGLEngine application, and handles one camera and the root node for you
 * @author Peter Bosch (AKA Peterbjorn)
 */
public abstract class SimpleApplication {

    protected Node scene;
    protected Camera camera;

    /**
     * Initialize PGLEngine
     * @param width Display width
     * @param height Display height
     */
    protected void initializePGLEngine(int width, int height) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            scene = new Node();
            camera = new Camera();
            camera.setViewport(new Vector2f(0, 0), new Vector2f(width, height));
            camera.setNearClip(0.1f);
            camera.setFarClip(100.0f);
            camera.setActive(true);
            scene.add(camera);
            glEnable(GL_DEPTH_TEST);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * This method is called when the application is launched
     */
    protected abstract void init();

    /**
     * This method is called every frame before rendering starts
     */
    protected abstract void preRender();

    /**
     * The entry point for this SimpleApplication
     */
    protected void main(){
        init();
        while (!Display.isCloseRequested()) {
            preRender();
            scene.render(null);
            Display.update();
        }
        Display.destroy();
    }


}
