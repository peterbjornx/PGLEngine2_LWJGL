package org.peterbjornx.pgl2.light;

import org.peterbjornx.pgl2.camera.Camera;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by IntelliJ IDEA.
 * User: Peter
 * Date: 6/14/11
 * Time: 12:43 AM
 * Computer: Peterbjornx-PC.rootdomain.asn.local (192.168.178.27)
 * Manages virtual lights.
 */
public class OpenGLLightManager {
    private OpenGLLight[] openGLLights = new OpenGLLight[8];
    private VirtualLight[] virtualLights = new VirtualLight[8];
    private boolean immediateActivation = false;
    private Camera currentCamera;

    /**
     * Maps a virtual light to a GL light.
     * @param virtualLight The virtual light to map
     * @return If the operation was successful
     */
    public boolean activateVirtualLight(VirtualLight virtualLight){
        int id;
        for (id = 0;id < 8;id++){
            if (virtualLights[id] == null)
                break;
            if (virtualLights[id].getPriority() < virtualLight.getPriority()){
                deactivateVirtualLight(virtualLights[id]);
                break;
            }
            if (id == 7)
                return false;
        }
        virtualLights[id] = virtualLight;
        openGLLights[id] = virtualLight.getOpenGLLight();
        openGLLights[id].setId(id);
        if (immediateActivation){
            openGLLights[id].enable();
            loadLightValues(openGLLights[id]);
        }
        return true;
    }

    private void loadLightValues(OpenGLLight light){
        glMatrixMode(GL_MODELVIEW);
        glPushMatrix();
        glLoadIdentity();
        currentCamera.loadViewMatrix();
        glPopMatrix();
    }

    /**
     * Unmaps an VirtualLight
     * @param virtualLight  The VirtualLight to unmap
     */
    public void deactivateVirtualLight(VirtualLight virtualLight){
        for (int id = 0;id < 8;id++){
            if (virtualLights[id] == virtualLight){
                if (immediateActivation)
                    openGLLights[id].disable();
                virtualLights[id] = null;
            }
        }
    }

    public void startLighting(Camera camera){
        currentCamera = camera;
        for (int id = 0;id < 8;id++)
            if (openGLLights[id] != null && virtualLights[id] != null){
                openGLLights[id].enable();
                loadLightValues(openGLLights[id]);
            } else if (openGLLights[id] != null){
                openGLLights[id].disable();
            }
        immediateActivation = true;
    }

    public void stopLighting(){
        for (int id = 0;id < 8;id++)
            if (openGLLights[id] != null)
                openGLLights[id].disable();
        immediateActivation = false;
    }
}
