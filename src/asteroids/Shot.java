package asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Point;

public class Shot implements GameObjects
{
    //The shot takes 
    public Shot(float x, float y, float direction)
    {
        if (sTexture == null)
                sTexture = new Texture(Gdx.files.internal(shipImage));
        
        shotDX += Math.cos(Math.toRadians(direction)) * shotInc;
        shotDY += Math.sin(Math.toRadians(direction)) * shotInc;
        shotX = x;
        shotY = y;
    }

    //Updates the shot
    @Override
    public void update(GameScreen s) 
    {
        shotX += shotDX;
        shotY += shotDY;
        timeLeft--;
        
            if (shotX < 0) 
            {
                shotX = AsteroidsApp.projectionWidth;
            }

            if (shotX > AsteroidsApp.projectionWidth) 
            {
                shotX = 0;
            }

            if (shotY < 0) 
            {
                shotY = AsteroidsApp.projectionHeight;
            }

            if (shotY > AsteroidsApp.projectionHeight) 
            {
                shotY = 0;
            }
    }

    //Renders the shot
    @Override
    public void render(SpriteBatch sBatch) 
    {
        sBatch.draw(sTexture, (int)shotX, (int)shotY, shotW, shotH,
				0, 0, shotW, shotH, false, true);
    }
    
    //Returns true if the shot has no time left, and false if the shot still has
    //time left to travel
    
    @Override
    public int getRadius() 
    {
        return (int) hitMid;
    }

    @Override
    public Point getCenter() 
    {
        return new Point((int) (shotX + shotMidX), (int) (shotY + shotMidY));
    }
    
    public void setDead()
    {
        dead = true;
    }
    
    public boolean isDead()
    {
        if (timeLeft == 0)
            dead = true;
        return dead;
    }
    
    public float getX()
    {
        return shotX;
    }
    
    public float getY()
    {
        return shotY;
    }
    
    private static Texture sTexture;
    private String shipImage = "src/asteroids/images/shot.png";
    
    private float shotDX;
    private float shotDY;
    private float shotX;
    private float shotY;
    
    private int shotW = 2;
    private int shotH = 2;
    private float shotMidX = shotW / 2;
    private float shotMidY = shotH / 2;
    private float hitMid = shotMidX;
    
    private static final float shotInc = 10f;
    
    private int timeLeft = 60;
    private boolean dead = false;
}