    import com.jme3.app.SimpleApplication;
    import com.jme3.light.DirectionalLight;
    import com.jme3.material.Material;
    import com.jme3.math.FastMath;
    import com.jme3.math.Vector2f;
    import com.jme3.math.Vector3f;
    import com.jme3.scene.Geometry;
    import com.jme3.scene.Mesh;
    import com.jme3.scene.shape.Box;
    import com.jme3.system.AppSettings;

    public class AsteroidsGame extends SimpleApplication {

        private Geometry geometry;

        @Override
        public void simpleInitApp() {
            // #1 create a box
            Mesh box = new Box(1,1,1);


            // #2 创建一个感光材质
            Material mat = new Material(assetManager,"Common/MatDefs/Light/Lighting.j3md");

            // #3 创建一个几何体，应用刚才和的网格和材质
            geometry = new Geometry("Box");
            geometry.setMesh(box);
            geometry.setMaterial(mat);

            // #4 创建一束定向光，并让他斜向下照射，好使我们能过
            DirectionalLight sun = new DirectionalLight();
            sun.setDirection(new Vector3f(-1, -2, -3));
            /* Configure cam to look at scene */
//            cam.setLocation(new Vector3f(0, 0, 6f));
//            cam.lookAt(new Vector3f(0, 0, 6f), Vector3f.ZERO);

            flyCam.setMoveSpeed(10);
            // #5 将方块和光源都添加到场景图中
            rootNode.attachChild(geometry);
            rootNode.addLight(sun);


        }
        @Override
        public void simpleUpdate(float tpf) {
            // time per frame ==> delta time in unity
            // S = VT 匀速运动
            // TODO
            float speed = FastMath.TWO_PI;
            geometry.rotate(0,tpf*speed,0);
        }

        public static void main(String[] args) {
            // 启动jME3程序
            AppSettings settings = new AppSettings(true);
            settings.setTitle("one game");
            settings.setResolution(480,720);
//            settings.setUseInput(false);
            AsteroidsGame app = new AsteroidsGame();
            app.setSettings(settings);
            app.setShowSettings(false);
            app.start();
        }

}
