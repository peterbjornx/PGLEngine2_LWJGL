package org.peterbjornx.pgl2.camera;

/**
 * User: Peter
 * Date: 6/9/11
 * Time: 5:57 PM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Describes the interface for various effects and settings to be applied to one or more cameras
 * @author Peter Bosch (AKA Peterbjorn)
 */
public interface RenderControl {

    /**
     * Enable this render control
     * @param cam The camera rendering the scene
     */
    void enable(Camera cam);

    /**
     * Disable this render control
     * @param cam The camera rendering the scene
     */
    void disable(Camera cam);

}
