package org.peterbjornx.pgl2.terrain;

import org.lwjgl.util.Color;
import org.peterbjornx.pgl2.texture.Texture2D;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/14/11
 * Time: 10:36 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public interface TerrainSource {
    int[][] getHeightMap();
    Color[][] getColorMap();
    int[][] getTextureMap();
    Texture2D getTexture(int id);
    int getTileSize();

    int getSideSize();
}
