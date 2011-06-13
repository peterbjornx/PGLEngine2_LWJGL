package org.peterbjornx.pgl2.buffer;

/**
 * User: Peter
 * Date: 6/12/11
 * Time: 9:15 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Describes the interface provided for common aspects of both element and geometry buffers
 * @author Peter Bosch (AKA Peterbjorn)
 */
public abstract interface OpenGLBuffer {
    /**
     * Activates this buffer
     */
    void bind();

    /**
     * Enables this buffer's capabilities
     */
    void enable();

    /**
     * Disables this buffer's capabilities
     */
    void disable();
}
