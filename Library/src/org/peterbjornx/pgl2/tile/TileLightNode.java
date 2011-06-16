package org.peterbjornx.pgl2.tile;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.light.OpenGLLight;
import org.peterbjornx.pgl2.light.VirtualLight;
import org.peterbjornx.pgl2.model.Node;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/14/11
 * Time: 12:43 AM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Represents a tile-based light
 */
public class TileLightNode extends Node implements VirtualLight{

    private int priority = 0;
    private OpenGLLight openGLLight = new OpenGLLight();
    //TODO: Implement this class

    /**
     * Does nothing as this is a tile-based light which gets activated by the TileManager
     * @param cam The camera that is rendering this node.
     * When the camera parameter is null only Camera nodes will render,
     */
    public void render(Camera cam){}

    public OpenGLLight getOpenGLLight() {
        openGLLight.setPosition(getAbsolutePosition());
        return openGLLight;
    }

    public int getPriority() {
        return priority;
    }

    public Color getAmbient() {
        return openGLLight.getAmbient();
    }

    public void setAmbient(Color ambient) {
        openGLLight.setAmbient(ambient);
    }

    public Color getDiffuse() {
        return openGLLight.getDiffuse();
    }

    public void setDiffuse(Color diffuse) {
        openGLLight.setDiffuse(diffuse);
    }

    public Color getSpecular() {
        return openGLLight.getSpecular();
    }

    public void setSpecular(Color specular) {
        openGLLight.setSpecular(specular);
    }

    public boolean isDirectional() {
        return openGLLight.isDirectional();
    }

    public void setDirectional(boolean directional) {
        openGLLight.setDirectional(directional);
    }
}
