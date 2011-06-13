package org.peterbjornx.pgl2.light;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/14/11
 * Time: 12:47 AM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 */
public interface VirtualLight {
    OpenGLLight getOpenGLLight();
    int getPriority();
}
