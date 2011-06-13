package org.peterbjornx.pgl2.tile;

import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.model.Node;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/13/11
 * Time: 11:19 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public class TileNode extends Node{
    private int tileX = 0;
    private int tileY = 0;
    private int tileZ = 0;

    public TileNode(int x,int y,int z){
        tileX = x;
        tileY = y;
        tileZ = z;
    }

    public void setParent(TileManagerNode parent) {
        this.parent = parent;
    }
}
