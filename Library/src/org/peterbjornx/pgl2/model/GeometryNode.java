package org.peterbjornx.pgl2.model;
import org.lwjgl.opengl.GL11;
import org.peterbjornx.pgl2.camera.Camera;

import static org.lwjgl.opengl.GL11.*;
import static org.peterbjornx.pgl2.gl.GLQM.*;

/**
 * User: Peter
 * Date: 6/9/11
 * Time: 4:17 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 *
 * This class provides the neccesary logic for a Node that has graphical content
 * @author Peter Bosch (AKA Peterbjorn)
 */
public abstract class GeometryNode extends Node{

    /**
     * Renders this node.
     * @param cam The camera that is rendering this node.
     */
    @Override
    public void render(Camera cam) {
        if (cam == null)
            return;
        glMatrixMode(GL11.GL_MODELVIEW); //Never assume clean state so switch to modelview
        glPushMatrix();//Push matrix so we can recover after render

        glTranslatevf(getAbsolutePosition());
        glRotateq(getAbsoluteRotation());

        renderGeometry(cam);

        glPopMatrix();
    }

    /**
     * Renders this nodes geometry
     * @param cam The camera that is rendering this node.
     */
    protected abstract void renderGeometry(Camera cam);
}
