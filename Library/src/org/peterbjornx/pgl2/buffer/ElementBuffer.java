package org.peterbjornx.pgl2.buffer;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/12/11
 * Time: 9:31 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public interface ElementBuffer {
    void addPolygon(List<Integer> points);
    void draw();
}
