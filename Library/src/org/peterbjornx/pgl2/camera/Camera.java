package org.peterbjornx.pgl2.camera;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.peterbjornx.pgl2.gl.GLQM.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.model.Node;

import java.nio.FloatBuffer;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/9/11
 * Time: 2:03 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class Camera extends Node {
    /* Multi-Camera Fields */
    private boolean active = false;
    /* Camera Fields */
    private Vector2f viewportPosition;
    private Vector2f viewportSize;
    private float farClip;
    private float nearClip;
    private Color clearColor = new Color(128,128,128,255);
    /* Matrix fields */
    private Matrix4f projectionMatrix = new Matrix4f();

    public void render(Camera cam){
        /* If we are being rendered by a camera or inactive, don't start rendering our view */
        if (cam != null ||  !active){
            super.render(cam);
            return;
        }
        /* Actual OpenGL projection setup */
        glViewport((int) viewportPosition.x, (int) viewportPosition.y, (int) viewportSize.x, (int) viewportSize.y);
        glMatrixMode(GL_PROJECTION);
        loadProjectionMatrix();
        glClearColor(clearColor.getRed() / 255.0f,
                clearColor.getBlue() / 255.0f,
                clearColor.getGreen() / 255.0f,
                clearColor.getAlpha() / 255.0f);
        /* View matrix setup */
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        loadViewMatrix();
        /* Clear the buffers */
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        /* Enable render controls */
        for (RenderControl control : renderControls)
                if (control != null)
                    control.enable(this);
        /* Render scene */
        Node rootNode = getRootNode();
        rootNode.render(this);
        drawAxises();
        /* Disable render controls */
        for (RenderControl control : renderControls)
                if (control != null)
                    control.disable(this);
    }

    private void drawAxises(){
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glTranslatef(-0.75f,-0.25f,-1);
        glRotateq(getAbsoluteRotation());
        glScalef(1,1,-1);
        glScalef(0.1f,0.1f,0.1f);
        glBegin(GL_LINES);
            glColor3f (1,0,0);
            glVertex3f(0,0,0);
            glVertex3f(1,0,0);

            glColor3f (0,1,0);
            glVertex3f(0,0,0);
            glVertex3f(0,1,0);

            glColor3f (0,0,1);
            glVertex3f(0,0,0);
            glVertex3f(0,0,1);
        glEnd();
    }

    private void loadProjectionMatrix(){
        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        projectionMatrix.store(matrixBuffer);
        matrixBuffer.flip();
        glLoadMatrix(matrixBuffer);
    }

    private void updateProjectionMatrix(){
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        gluPerspective(45.0f,viewportSize.x / viewportSize.y,nearClip,farClip);

        FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
        glGetFloat(GL_PROJECTION_MATRIX, matrixBuffer);
        projectionMatrix.load(matrixBuffer);
        glPopMatrix();
    }

    public void loadViewMatrix() {
        glScalef(1,1,-1);
        glRotateq(getAbsoluteRotation());
        glTranslatevf((Vector3f) getAbsolutePosition().negate());
    }

    public Vector2f getViewportPosition() {
        return viewportPosition;
    }

    public Vector2f getViewportSize() {
        return viewportSize;
    }

    public void setViewport(Vector2f position, Vector2f size) {
        this.viewportPosition = position;
        this.viewportSize = size;
    }

    public float getFarClip() {
        return farClip;
    }

    public void setFarClip(float farClip) {
        this.farClip = farClip;
        updateProjectionMatrix();
    }

    public float getNearClip() {
        return nearClip;
    }

    public void setNearClip(float nearClip) {
        this.nearClip = nearClip;
        updateProjectionMatrix();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
