package org.peterbjornx.pgl2.light;

import static org.lwjgl.opengl.GL11.*;
import static org.peterbjornx.pgl2.gl.GLQM.*;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/10/11
 * Time: 5:47 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class OpenGLLight {

    private Color ambient;
    private Color diffuse;
    private Color specular;
    private Vector3f position;
    private int id = 0;
    private boolean directional;

    public void loadValues(){
        glLight(GL_LIGHT0 + id,GL_AMBIENT,getColour4fBuffer(ambient));
        glLight(GL_LIGHT0 + id,GL_DIFFUSE,getColour4fBuffer(diffuse));
        glLight(GL_LIGHT0 + id,GL_SPECULAR,getColour4fBuffer(specular));
        glLight(GL_LIGHT0 + id, GL_POSITION, getVector4fBuffer(position, directional ? 0 : 1));
    }

    public void enable(){
        glEnable(GL_LIGHT0 + id);
    }

    public void disable(){
        glDisable(GL_LIGHT0 + id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getAmbient() {
        return ambient;
    }

    public void setAmbient(Color ambient) {
        this.ambient = ambient;
    }

    public Color getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }

    public Color getSpecular() {
        return specular;
    }

    public void setSpecular(Color specular) {
        this.specular = specular;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public boolean isDirectional() {
        return directional;
    }

    public void setDirectional(boolean directional) {
        this.directional = directional;
    }
}
