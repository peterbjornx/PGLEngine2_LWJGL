package org.peterbjornx.pgl2.terrain;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.model.GeometryNode;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/14/11
 * Time: 10:35 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class Terrain extends GeometryNode {
    private TerrainSource terrainSource;
    private TerrainLayer[] terrainLayers;
    private Vector3f[][] normalMap;

    public Terrain(TerrainSource terrainSource) {
        this.terrainSource = terrainSource;
        generateNormalMap();
        generateTerrainLayers();
    }


    private void generateNormalMap() {
        int[][] hm_ = terrainSource.getHeightMap();
        int side = terrainSource.getSideSize();
        normalMap = new Vector3f[side][side];
        int tileZ;
        int tileX;
        for (tileZ = 0; tileZ < side; tileZ++)
            for (tileX = 0; tileX < side; tileX++)
                normalMap[tileX][tileZ]= new Vector3f(0,1,0);
        for (tileZ = 1; tileZ < side-1; tileZ++) {
            for (tileX = 1; tileX < side-1; tileX++) {
                int diff1 = hm_[tileX + 1][tileZ] - hm_[tileX - 1][tileZ];
                int diff2 = hm_[tileX][tileZ + 1] - hm_[tileX][tileZ - 1];
                float div = (float) Math.sqrt((double) (diff2 * diff2 + 65536 + diff1 * diff1));
                normalMap[tileX][tileZ] = new Vector3f((float) diff1 / div, -256.0F / div, (float) diff2 / div);
            }
        }
    }

    private void generateTerrainLayers() {
        List<Integer> textures = new ArrayList<Integer>();
        int[][] textureMap = terrainSource.getTextureMap();
        for (int x = 0; x < textureMap.length; x++)
            for (int z = 0; z < textureMap[x].length; z++)
                if (!textures.contains(textureMap[x][z]))
                    textures.add(textureMap[x][z]);
        terrainLayers = new TerrainLayer[textures.size()];
        for (int layerId = 0; layerId < terrainLayers.length; layerId++)
            terrainLayers[layerId] = new TerrainLayer(this, textures.remove(0));
    }

    /**
     * Renders this nodes geometry
     *
     * @param cam The camera that is rendering this node.
     */
    @Override
    protected void renderGeometry(Camera cam) {
        terrainLayers[0].enable();
        glDepthFunc(GL_LEQUAL);
        terrainLayers[0].render();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        for (int layerId = 1; layerId < terrainLayers.length; layerId++)
            terrainLayers[layerId].render();
        glDisable(GL_BLEND);
        terrainLayers[0].disable();
        glDepthFunc(GL_LESS);
    }

    public TerrainSource getTerrainSource() {
        return terrainSource;
    }

    public Vector3f[][] getNormalMap() {
        return normalMap;
    }

    public void update(){
        generateNormalMap();
        generateTerrainLayers();
    }

    public void setTerrainSource(TerrainSource terrainSource) {
        this.terrainSource = terrainSource;
        update();
    }
}
