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
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/10/11
 * Time: 12:18 AM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public abstract class SimpleApplication {

    protected Node scene;
    protected Camera camera;

    protected void initializeLWJGL(int width, int height) {
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

    protected abstract void init();

    protected abstract void preRender();

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
