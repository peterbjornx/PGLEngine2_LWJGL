package org.peterbjornx.pgl2.tile;

import com.sun.rowset.internal.InsertRow;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.camera.Camera;
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

    public void enableLightsForTile(){

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
                for (TileNode __t : _t)
                    __t.render(cam);
    }

    /**
     * Adds a child node.
     *
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
        return super.getChildren();    //To change body of overridden methods use File | Settings | File Templates.
    }
}
