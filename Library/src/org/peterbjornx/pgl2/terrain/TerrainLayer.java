package org.peterbjornx.pgl2.terrain;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.*;
import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Vector;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.buffer.ElementBuffer;
import org.peterbjornx.pgl2.buffer.GeometryBuffer;
import org.peterbjornx.pgl2.buffer.OpenGLBufferFactory;
import org.peterbjornx.pgl2.buffer.impl.StubBuffer;
import org.peterbjornx.pgl2.texture.Texture2D;
import org.peterbjornx.pgl2.util.PglException;

import java.awt.event.HierarchyListener;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/14/11
 * Time: 10:35 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class TerrainLayer {
    private GeometryBuffer geometryBuffer;
    private ElementBuffer elementBuffer;
    private Terrain terrain;
    private Texture2D texture;
    private int textureId;

    public TerrainLayer(Terrain terrain, int textureId) {
        this.terrain = terrain;
        this.texture = terrain.getTerrainSource().getTexture(textureId);
        this.textureId = textureId;
        int[][] heightmap = terrain.getTerrainSource().getHeightMap();
        int side = terrain.getTerrainSource().getSideSize();
        int totalTiles = side * side;
        int totalPolys = (side-1) * (side-1);
        geometryBuffer = /*new StubBuffer(false, GL11.GL_TRIANGLES);//*/OpenGLBufferFactory.createGeometryBuffer(totalPolys*4,false);
        elementBuffer = /*(StubBuffer) geometryBuffer;//*/OpenGLBufferFactory.createElementBuffer(totalPolys, GL11.GL_QUADS);
        for (int x = 0;x < side-1;x++)
            for (int z = 0;z < side-1;z++)
                try {
                    addTile(x,z);
                } catch (PglException e) {
                    e.printStackTrace();
                }

    }

    public void addTile(int x,int z) throws PglException {
        int[][] heightmap = terrain.getTerrainSource().getHeightMap();
        Color[][] colormap = terrain.getTerrainSource().getColorMap();
        int[][] texturemap = terrain.getTerrainSource().getTextureMap();
        Vector3f[][] normalmap = terrain.getNormalMap();
        int tileSize = terrain.getTerrainSource().getTileSize();
        int lii[] = new int[4];
        int idx = 0;
        ArrayList<Integer> vertexList = new ArrayList<Integer>();
        for (int _x = x;_x < (x+2);_x++)
            for (int _z = z;_z < (z+2);_z++){
                Vector3f position = new Vector3f(_x*tileSize,heightmap[_x][_z],_z*tileSize);
                Color tileColor = colormap[_x][_z];
                tileColor.setAlpha((texturemap[_x][_z] == textureId) ? 255 : 0);
                lii[idx++] = geometryBuffer.addVertex(position, normalmap[x][z], new Vector3f(_x - x, _z - z, 0), tileColor);
            }
        if (Math.abs(heightmap[x][z] - heightmap[x+1][z+1]) < Math.abs(heightmap[x+1][z] - heightmap[x][z+1])
               ){
            vertexList.add(lii[0]);
            vertexList.add(lii[1]);
            vertexList.add(lii[2]);
            elementBuffer.addPolygon(vertexList);
            vertexList = new ArrayList<Integer>();
            vertexList.add(lii[1]);
            vertexList.add(lii[3]);
            vertexList.add(lii[2]);
        } else {
            vertexList.add(lii[0]);
            vertexList.add(lii[1]);
            vertexList.add(lii[3]);
            elementBuffer.addPolygon(vertexList);
            vertexList = new ArrayList<Integer>();
            vertexList.add(lii[0]);
            vertexList.add(lii[3]);
            vertexList.add(lii[2]);
        }
        elementBuffer.addPolygon(vertexList);
    }

    public void enable(){
        texture.enable();
        elementBuffer.enable();
        geometryBuffer.enable();
    }

    public void disable(){
        elementBuffer.enable();
        geometryBuffer.enable();
    }

    public void render() {
        texture.bind();
        geometryBuffer.bind();
        elementBuffer.bind();
        elementBuffer.draw();
    }
}
