package org.peterbjornx.pgl2.camera.controls;

import static org.lwjgl.opengl.GL11.*;
import static org.peterbjornx.pgl2.gl.GLQM.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Color;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.camera.RenderControl;

import java.nio.FloatBuffer;

/**
 * User: Peter
 * Date: 6/9/11
 * Time: 6:07 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Implements OpenGL fog
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class FogRenderControl implements RenderControl {
    private float density = 0.3f;
    private Color colour = new Color(128,128,128,255);
    private float start = 1.0f;
    private float end =  5.0f;
    private int mode = GL_EXP2;
    private int hint = GL_NICEST;

    /**
     * Enables the fog.
     * @param cam The camera rendering the scene
     */
    public void enable(Camera cam) {
        glFogi(GL_FOG_MODE, mode);
        glFog(GL11.GL_FOG_COLOR, getColour4fBuffer(colour));
        glFogf(GL_FOG_DENSITY, density);
        glHint(GL_FOG_HINT, GL_DONT_CARE);
        glFogf(GL_FOG_START, start);
        glFogf(GL_FOG_END, end);

        glEnable(GL_FOG);
    }

    /**
     * Disables the fog.
     * @param cam The camera rendering the scene
     */
    public void disable(Camera cam) {
        glDisable(GL_FOG);
    }

    /**
     * Gets the OpenGL quality hint.
     * @return The OpenGL quality hint.
     */
    public int getHint() {
        return hint;
    }

    /**
     * Sets the OpenGL quality hint.
     * @param hint The OpenGL quality hint.
     */
    public void setHint(int hint) {
        this.hint = hint;
    }

    /**
     * Gets the fog colour
     * @return The fog colour
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Sets the fog colour
     * @param colour The fog colour
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Sets the fog start depth
     * @return The fog start depth
     */
    public float getStart() {
        return start;
    }

    /**
     * Sets the fog start depth
     * @param start the fog start depth
     */
    public void setStart(float start) {
        this.start = start;
    }

    /**
     * Gets the fog end depth
     * @return the fog end depth
     */
    public float getEnd() {
        return end;
    }

    /**
     * Sets the fog end depth
     * @param end The fog end depth
     */
    public void setEnd(float end) {
        this.end = end;
    }

    /**
     * Gets the fog mode
     * @return The fog mode
     */
    public int getMode() {
        return mode;
    }

    /**
     * Sets the fog mode
     * @param mode The fog mode
     */
    public void setMode(int mode) {
        this.mode = mode;
    }

    /**
     * Gets the fog density
     * @return The fog density
     */
    public float getDensity() {
        return density;
    }

    /**
     * Sets the fog density
     * @param density The fog density
     */
    public void setDensity(float density) {
        this.density = density;
    }
}
