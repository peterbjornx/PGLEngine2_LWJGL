package org.peterbjornx.pgl2.buffer;

import org.peterbjornx.pgl2.util.PglException;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/12/11
 * Time: 9:31 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public interface ElementBuffer extends OpenGLBuffer{
    void addPolygon(List<Integer> points) throws PglException;
    void draw();
}
