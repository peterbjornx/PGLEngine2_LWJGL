package org.peterbjornx.pgl2.model;
import org.lwjgl.opengl.GL11;
import org.peterbjornx.pgl2.camera.Camera;

import static org.lwjgl.opengl.GL11.*;
import static org.peterbjornx.pgl2.gl.GLQM.*;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/9/11
 * Time: 4:17 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public abstract class GeometryNode extends Node{

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

    protected abstract void renderGeometry(Camera cam);
}
