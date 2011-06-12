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
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/9/11
 * Time: 6:07 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class FogRenderControl implements RenderControl {
    private float density = 0.3f;
    private Color colour = new Color(128,128,128,255);
    private float start = 1.0f;
    private float end =  5.0f;
    private int mode = GL_EXP2;
    private int hint = GL_NICEST;

    public void enable(Camera cam) {
        glFogi(GL_FOG_MODE, mode);
        glFog(GL11.GL_FOG_COLOR, getColour4fBuffer(colour));
        glFogf(GL_FOG_DENSITY, density);
        glHint(GL_FOG_HINT, GL_DONT_CARE);
        glFogf(GL_FOG_START, start);
        glFogf(GL_FOG_END, end);

        glEnable(GL_FOG);
    }

    public void disable(Camera cam) {
        glDisable(GL_FOG);
    }

    public int getHint() {
        return hint;
    }

    public void setHint(int hint) {
        this.hint = hint;
    }

    public Color getColour() {
        return colour;
    }

    public void setColour(Color colour) {
        this.colour = colour;
    }

    public float getStart() {
        return start;
    }

    public void setStart(float start) {
        this.start = start;
    }

    public float getEnd() {
        return end;
    }

    public void setEnd(float end) {
        this.end = end;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }
}
