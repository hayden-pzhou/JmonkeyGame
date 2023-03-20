package control.system;

import com.jme3.app.DebugKeysAppState;
import com.jme3.app.FlyCamAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.AppState;
import com.jme3.audio.AudioListenerState;
import com.jme3.math.Vector3f;
import control.sprite.SpriteManager;

public abstract class Simple2DApplication extends SimpleApplication {

    private SpriteManager spriteManager;
    private float aspect;

    public Simple2DApplication(){
        super(new StatsAppState(),new FlyCamAppState(),new AudioListenerState(),new DebugKeysAppState());
    }

    public Simple2DApplication(AppState... initialStates){
        super(initialStates);
    }

    @Override
    public void simpleInitApp() {

    }

    private void InitCamera(){
        stateManager.detach(stateManager.getState(FlyCamAppState.class));
        renderManager.setCamera(cam,true);

        cam.setParallelProjection(true);

        cam.setLocation(new Vector3f(0f,0f,10f));

        setCameraSize(3.5f);
    }

    /**
     * Changes the size of the camera
     *
     */
    public void setCameraSize(float size){
        aspect = (float) cam.getWidth()/cam.getHeight();
        cam.setFrustum(-1000,1000,-aspect*size,aspect*size,size,-size);
    }
    public abstract void simpleInit2DAPP();
}
