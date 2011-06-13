package org.peterbjornx.pgl2.tile;

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

    //TODO: Implement this class

    /**
     * Does nothing as this is a tile-based light which gets activated by the TileManager
     * @param cam The camera that is rendering this node.
     * When the camera parameter is null only Camera nodes will render,
     */
    public void render(Camera cam){}

    public OpenGLLight getOpenGLLight() {
        return null;
    }

    public int getPriority() {
        return 0;
    }

}
