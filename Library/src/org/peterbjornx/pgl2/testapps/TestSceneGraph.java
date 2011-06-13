package org.peterbjornx.pgl2.testapps;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.geom.CubeNode;
import org.peterbjornx.pgl2.input.cameracontrol.FirstPersonCamera;
import org.peterbjornx.pgl2.util.SimpleApplication;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/9/11
 * Time: 8:16 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class TestSceneGraph extends SimpleApplication{
    private FirstPersonCamera fpsCamera = new FirstPersonCamera();
    public static void main(String[] args) {
        new TestSceneGraph().main();
    }

    @Override
    protected void init() {
        initializePGLEngine(1200, 600);
        CubeNode cube = new CubeNode();
        cube.setPosition(new Vector3f(0.5f,4.0f,0.2f));
        scene.add(cube);
        cube = new CubeNode();
        cube.setPosition(new Vector3f(0.5f,3.0f,0.3f));
        scene.add(cube);
        cube = new CubeNode();
        cube.setPosition(new Vector3f(1.0f,3.5f,0.2f));
        scene.add(cube);
        cube = new CubeNode();
        cube.setPosition(new Vector3f(0.5f,5.0f,0.4f));
        scene.add(cube);
        cube = new CubeNode();
        cube.setPosition(new Vector3f(0.5f,4.0f,2.2f));
        scene.add(cube);
    }

    @Override
    protected void preRender() {
        fpsCamera.handleInput(camera);
        Display.setTitle("Camera Rot:("+camera.getAbsoluteRotation()+") LRot:("+camera.getRotation()+")");
    }
}
