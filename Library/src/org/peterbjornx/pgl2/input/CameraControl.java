package org.peterbjornx.pgl2.input;

import org.peterbjornx.pgl2.camera.Camera;

/**
 * User: Peter
 * Date: 6/10/11
 * Time: 12:30 AM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Describes a camera control mechanism, only to be used by demos as these don't provide any logic besides camera movement.
 * @author Peter Bosch (AKA Peterbjorn)
 */
public interface CameraControl {
    /**
     * Handles user input and moves the camera accordingly
     * @param camera The camera to manipulate
     */
    public void handleInput(Camera camera);
}
