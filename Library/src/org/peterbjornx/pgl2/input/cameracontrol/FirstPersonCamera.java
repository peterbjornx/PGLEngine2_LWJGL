package org.peterbjornx.pgl2.input.cameracontrol;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Quaternion;
import org.lwjgl.util.vector.Vector3f;
import org.peterbjornx.pgl2.camera.Camera;
import org.peterbjornx.pgl2.input.CameraControl;
import org.peterbjornx.pgl2.math.VectorMath;

import static java.lang.Math.*;
/**
 * User: Peter
 * Date: 6/10/11
 * Time: 12:29 AM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Implements a first person WASD camera. <BR/>
 * Camera controls are  :WASD- Movement, Mouse-Rotation,IL-Look sensitivity,OL- Walkspeed
 * @author Peter Bosch (AKA Peterbjorn)
 */
public class FirstPersonCamera implements CameraControl{

    private float lookSpeed = 0.25f;
    private float walkSpeed = 0.005f;
    private boolean lookDirty = true;

    /**
     * Handles user input and moves the camera accordingly
     * @param camera The camera to manipulate
     */
    public void handleInput(Camera camera) {
        if (camera == null)
            return;//TODO: Decide if we need to throw exception
        Vector3f cameraPosition = camera.getPosition();
        Quaternion cameraRotation = camera.getRotation();
        if (Mouse.isButtonDown(0)){
            if (lookDirty){
                Mouse.getDX();        //Initialize delta coordinates
                Mouse.getDY();
                lookDirty = false;
            }
            cameraRotation = Quaternion.mul(cameraRotation,
                    VectorMath.axisAngleToQuaternion(new Vector3f(-1,0,0),Mouse.getDY() * lookSpeed),null);
            cameraRotation = Quaternion.mul(
                    VectorMath.axisAngleToQuaternion(new Vector3f(0,1,0),Mouse.getDX() * lookSpeed),cameraRotation,null);
        } else
            lookDirty = true;
        camera.setRotation(cameraRotation);
        Quaternion absCameraRotation = camera.getAbsoluteRotation();
        if (Keyboard.isKeyDown(Keyboard.KEY_W)){
            cameraPosition = Vector3f.add(cameraPosition,VectorMath.quatRotate(absCameraRotation,new Vector3f(0,0,walkSpeed)),null);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)){
            cameraPosition = Vector3f.add(cameraPosition,VectorMath.quatRotate(absCameraRotation,new Vector3f(0,0,-walkSpeed)),null);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_I)){
            lookSpeed += 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_K)){
            lookSpeed -= 0.01f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_O)){
            walkSpeed += 0.001f;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_L)){
            walkSpeed -= 0.001f;
        }
        camera.setPosition(cameraPosition);
    }
}
