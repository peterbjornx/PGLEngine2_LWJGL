package org.peterbjornx.pgl2.light;

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
            openGLLights[id].loadValues();
        }
        return true;
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

    public void startLighting(){
        for (int id = 0;id < 8;id++)
            if (openGLLights[id] != null && virtualLights[id] != null){
                openGLLights[id].enable();
                openGLLights[id].loadValues();
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
