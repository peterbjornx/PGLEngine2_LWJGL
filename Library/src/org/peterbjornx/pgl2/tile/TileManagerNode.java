package org.peterbjornx.pgl2.tile;

import com.sun.rowset.internal.InsertRow;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.light.OpenGLLightManager;
import org.peterbjornx.pgl2.model.Node;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 11:16 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class TileManagerNode extends Node{
    private TileNode tileArray[][][];
    private static final int LIGHT_RANGE = 1;
    private boolean tileLightingEnabled = false;
    private OpenGLLightManager openGLLightManager;

    /**
     * Creates a new TileManager node
     * @param height Height of the tile map
     * @param width Width of the tile map
     * @param depth Depth of the tile map
     * @param tileHeight Height of a single tile
     * @param tileWidth Width of a single tile
     * @param tileDepth Depth of a single tile
     */
    public TileManagerNode(int height,int width,int depth,int tileHeight,int tileWidth,int tileDepth) {
        tileArray = new TileNode[height][width][depth];
        for (int y = 0;y < height;y++)
            for (int x = 0;x < width;x++)
                for (int z = 0;z < depth;z++){
                    tileArray[y][x][z] = new TileNode(x,y,z);
                    tileArray[y][x][z].setPosition(new Vector3f(x * tileWidth,y * tileHeight,z * tileDepth));
                    tileArray[y][x][z].setParent(this);
                }
    }

    private void activateLightsForTile(int y,int x,int z){
        LinkedList<TileNode> tiles = getTileRange(y,x-LIGHT_RANGE,z-LIGHT_RANGE,(LIGHT_RANGE*2)+1,(LIGHT_RANGE*2)+1);
        for (TileNode tileNode : tiles)
            for (Node node : tileNode.getChildrenByType(TileLightNode.class))
                openGLLightManager.activateVirtualLight((TileLightNode) node);
    }

    private void deactivateLightsForTile(int y,int x,int z){
        LinkedList<TileNode> tiles = getTileRange(y,x-LIGHT_RANGE,z-LIGHT_RANGE,(LIGHT_RANGE*2)+1,(LIGHT_RANGE*2)+1);
        for (TileNode tileNode : tiles)
            for (Node node : tileNode.getChildrenByType(TileLightNode.class))
                openGLLightManager.deactivateVirtualLight((TileLightNode) node);
    }

    /**
     * Gets a range of tiles
     * @param y Height level to get tiles from
     * @param x X origin coordinate
     * @param z Z origin coordinate
     * @param w Width of area
     * @param h Height of area
     * @return A list of tiles in the given area
     */
    public LinkedList<TileNode> getTileRange(int y,int x,int z,int w,int h){
        LinkedList<TileNode> tiles = new LinkedList<TileNode>();
        if (y > tileArray.length)
            y = tileArray.length - 1;
        if (x >= tileArray[0].length)
            x = tileArray[0].length - 1;
        if (x+w > tileArray[0].length)
            w = tileArray[0].length-x;
        if (z >= tileArray[0][0].length)
            z = tileArray[0][0].length - 1;
        if (z+h > tileArray[0][0].length)
            h = tileArray[0][0].length-z;
        for (int _x = x;_x < (x+w);_x++)
            for (int _z = 0;_z < (z+h);_z++)
                if (tileArray[y][_x][_z] != null)
                        tiles.add(tileArray[y][_x][_z]);
      return tiles;
    }

    /**
     * Gets a single tile at the given coordinates
     * @param y The height level
     * @param x X Coordinate
     * @param z Z Coordinate
     * @return The tile
     */
    public TileNode getTile(int y,int x,int z){
        return tileArray[y][x][z];
    }

    /**
     * Renders this node and its children
     *
     * @param cam The camera that is rendering this node.
     *            When the camera parameter is null only Camera nodes will render,
     *            this is used to have all active cameras render.
     */
    @Override
    public void render(Camera cam) {
        for (TileNode[][] t : tileArray)
            for (TileNode[] _t : t)
                for (TileNode __t : _t)   {
                    if(tileLightingEnabled)
                        activateLightsForTile(__t.getTileY(),__t.getTileX(),__t.getTileZ());
                    openGLLightManager.startLighting();
                    __t.render(cam);
                    openGLLightManager.stopLighting();
                    if(tileLightingEnabled)
                        deactivateLightsForTile(__t.getTileY(), __t.getTileX(), __t.getTileZ());
                }
    }

    /**
     * Not valid for a TileManagerNode, use getTile(y,x,z).add(child) instead
     * @param child The child to add.
     */
    @Override
    public void add(Node child) {
        throw new RuntimeException("Not supported");
    }

    /**
     * Gets a list of this node's children
     *
     * @return A list of this node's children
     */
    @Override
    public LinkedList<Node> getChildren() {
        LinkedList<Node> children = new LinkedList<Node>();
        for (TileNode[][] t : tileArray)
            for (TileNode[] _t : t)
                for (TileNode __t : _t)
                    if (__t != null)
                        children.add(__t);
        return children;
    }

    /**
     * Sets the OpenGLLightManager that will be used for the tile lighting
     * @param openGLLightManager The OpenGLLightManager that will be used for the tile lighting
     */
    public void setOpenGLLightManager(OpenGLLightManager openGLLightManager) {
        this.openGLLightManager = openGLLightManager;
    }

    /**
     * Returns if tile based lighting is enabled
     * @return if tile based lighting is enabled
     */
    public boolean isTileLightingEnabled() {
        return tileLightingEnabled;
    }

    /**
     * Sets if tile based lighting is enabled
     * @param tileLightingEnabled If tile based lighting should be enabled
     */
    public void setTileLightingEnabled(boolean tileLightingEnabled) {
        this.tileLightingEnabled = tileLightingEnabled;
    }
}
