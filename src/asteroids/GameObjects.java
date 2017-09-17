package asteroids;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Point;

//An interface for all objects that will be on the Gamescreen
public interface GameObjects 
{
    public void update(GameScreen s);
    public void render(SpriteBatch sBatch);
    public int getRadius();
    public Point getCenter();
    public void setDead();
    public boolean isDead();
    public float getX();
    public float getY();
}
