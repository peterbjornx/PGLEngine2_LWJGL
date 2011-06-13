package org.peterbjornx.pgl2.buffer;

import org.peterbjornx.pgl2.util.PglException;

import java.util.List;

/**
 * User: Peter
 * Date: 6/12/11
 * Time: 9:31 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Describers the interface provided for element buffers
 * @author Peter Bosch (AKA Peterbjorn)
 */
public interface ElementBuffer extends OpenGLBuffer{
    /**
     * Add an polygon (element) to this buffer
     * @param points The vertex indices describing this polygon
     * @throws PglException When this buffer is already uploaded<BR/> to the server or when the polygon index count doesn't match current drawing mode
     */
    void addPolygon(List<Integer> points) throws PglException;

    /**
     * Renders the contents of this element buffer.
     * Note: requires that the element and geometry buffers have been bound and enabled
     */
    void draw();
}
