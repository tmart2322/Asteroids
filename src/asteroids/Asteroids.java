package asteroids;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Asteroids 
{

    public static void main(String[] args) 
    {
        boolean fullScreen = true;
        LwjglApplicationConfiguration config =
                new LwjglApplicationConfiguration();

        if (fullScreen) 
        {
            // Full-screen setup
            config.title = "Asteroids";
            Graphics.DisplayMode deskMode =
                    LwjglApplicationConfiguration.getDesktopDisplayMode();
            config.width = deskMode.width;
            config.height = deskMode.height;
            config.fullscreen = false;
            config.vSyncEnabled = false;
        } 
        
        else 
        {
            // Windowed setup
            config.title = "Asteroids";
            config.width = 800;
            config.height = 600;
            config.fullscreen = false;
            config.vSyncEnabled = false;
        }

        LwjglApplication app = new LwjglApplication(new AsteroidsApp(), config);

    }
}
