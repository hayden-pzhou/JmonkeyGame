package game;


import com.jme3.app.SimpleApplication;

public class MyGame extends SimpleApplication {

    public MyGame(){
        super();
    }

    @Override
    public void simpleInitApp() {

    }

    public static void main(String[] args) {
        MyGame app = new MyGame();
        app.start();
    }

}
