package org.peterbjornx.pgl2.buffer.impl;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/16/11
 * Time: 7:58 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.buffer.ElementBuffer;
import org.peterbjornx.pgl2.buffer.GeometryBuffer;
import org.peterbjornx.pgl2.util.PglException;
import static org.lwjgl.opengl.GL11.*;
import java.util.*;
import java.util.List;

/**
 * TODO: Add class description.
 *
 * @author Peterbjornx
 */
public class StubBuffer implements GeometryBuffer,ElementBuffer {
    private int vc = 0;
    private HashMap<Integer,GLVertex> vertex = new HashMap<Integer,GLVertex>();
    private List<List<Integer>> polys = new Vector<List<Integer>>();
    private int drawmode;
    private boolean useWTexCoord = false;
    public static final boolean USE = false;

    public StubBuffer(boolean useWTexCoord, int drawmode) {
        this.useWTexCoord = useWTexCoord;
        this.drawmode = drawmode;
    }
    public int addVertex(Vector3f pos, Vector3f normal, Vector3f texcoord, Color colour) {
        vertex.put(vc,new GLVertex(pos,normal,texcoord,colour));
        vc++;
        return vc-1;
    }

    public int getVertexCount() {
        return vc;
    }

    public void addPolygon(java.util.List<Integer> points) throws PglException {
        polys.add(points);
    }

    public void draw() {
        glBegin(drawmode);
        for (List<Integer> g : polys){
            for (Integer p : g){
                GLVertex v = vertex.get(p);
               // System.out.println(p);
                Vector3f pos = v.getPos();
                Vector3f n = v.getNormal();
                Vector3f tc = v.getTexcoord();
                Color c = v.getColour();
                glColor4f(c.getRed() / 255.0f, c.getGreen() / 255.0f, c.getBlue() / 255.0f, c.getAlpha() / 255.0f);//c.getAlpha() / 255.0f);
                glNormal3f(n.getX(), n.getY(), n.getZ());
                if (!useWTexCoord)
                   glTexCoord2f(tc.getX(),tc.getY());
                else
                    glTexCoord3f(tc.getX(), tc.getY(), tc.getZ());
                glVertex3f(pos.getX(), pos.getY(), pos.getZ());
            }
        }
        glEnd();
    }

    public void bind() {}

    public void enable() {}

    public void disable() {}

    public void cleanupClientMemory() {}

    public void cleanupServerMemory() {}

    private class GLVertex {
        private Vector3f pos;
        private Vector3f normal;
        private Vector3f texcoord;
        private Color colour;

        public GLVertex(Vector3f pos, Vector3f normal, Vector3f texcoord, Color colour) {
            this.pos = pos;
            this.normal = normal;
            this.texcoord = texcoord;
            this.colour = colour;
        }


        public Vector3f getPos() {
            return pos;
        }

        public void setPos(Vector3f pos) {
            this.pos = pos;
        }

        public Vector3f getNormal() {
            return normal;
        }

        public void setNormal(Vector3f normal) {
            this.normal = normal;
        }

        public Vector3f getTexcoord() {
            return texcoord;
        }

        public void setTexcoord(Vector3f texcoord) {
            this.texcoord = texcoord;
        }

        public Color getColour() {
            return colour;
        }

        public void setColour(Color colour) {
            this.colour = colour;
        }
    }
}
