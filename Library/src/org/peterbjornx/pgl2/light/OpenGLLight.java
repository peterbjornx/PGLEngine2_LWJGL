package org.peterbjornx.pgl2.light;

import static org.lwjgl.opengl.GL11.*;
import static org.peterbjornx.pgl2.gl.GLQM.*;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;

/**
 * User: Peter
 * Date: 6/10/11
 * Time: 5:47 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Provides an easy interface to OpenGL lights, avoid using this outside of PGLEngine2
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class OpenGLLight {

    private Color ambient;
    private Color diffuse;
    private Color specular;
    private Vector3f position;
    private int id = 0;
    private boolean directional;

    /**
     * Loads the light's values into the OpenGL light state.
     */
    public void loadValues(){
        glLight(GL_LIGHT0 + id,GL_AMBIENT,getColour4fBuffer(ambient));
        glLight(GL_LIGHT0 + id,GL_DIFFUSE,getColour4fBuffer(diffuse));
        glLight(GL_LIGHT0 + id,GL_SPECULAR,getColour4fBuffer(specular));
        glLight(GL_LIGHT0 + id, GL_POSITION, getVector4fBuffer(position, directional ? 0 : 1));
    }

    /**
     * Enables this light
     */
    public void enable(){
        glEnable(GL_LIGHT0 + id);
    }

    /**
     * Disables this light
     */
    public void disable(){
        glDisable(GL_LIGHT0 + id);
    }

    /**
     * Gets this light's ID
     * @return This light's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets this light's ID
     * @param id This light's ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets this light's ambient colour
     * @return This light's ambient colour
     */
    public Color getAmbient() {
        return ambient;
    }

    /**
     * Sets this light's ambient colour
     * @param ambient This light's ambient colour
     */
    public void setAmbient(Color ambient) {
        this.ambient = ambient;
    }

    /**
     * Gets this light's diffuse colour
     * @return This light's diffuse colour
     */
    public Color getDiffuse() {
        return diffuse;
    }

    /**
     * Sets this light's diffuse colour
     * @param diffuse This light's diffuse colour
     */
    public void setDiffuse(Color diffuse) {
        this.diffuse = diffuse;
    }

    /**
     * Gets this light's specular colour
     * @return This light's specular colour
     */
    public Color getSpecular() {
        return specular;
    }

    /**
     * Sets this light's specular colour
     * @param specular This light's specular colour
     */
    public void setSpecular(Color specular) {
        this.specular = specular;
    }

    /**
     * Gets this light's position
     * @return This light's position
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Sets this light's position
     * @param position this light's position
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Return whether if this light is directional or not
     * @return if this light is directional or not
     */
    public boolean isDirectional() {
        return directional;
    }

    /**
     * Sets this light as directional or positional
     * @param directional Is this light directional
     */
    public void setDirectional(boolean directional) {
        this.directional = directional;
    }
}
