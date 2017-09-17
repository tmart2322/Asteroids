package asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Point;

public class Asteroid implements GameObjects
{
    public enum Size 
    {

        LARGE(96, 1.0f, 50, "src/asteroids/images/asteroidlarge.png"), 
        MEDIUM(48, 2.0f, 100, "src/asteroids/images/asteroidmed.png"), 
        SMALL(24, 3.0f, 250, "src/asteroids/images/asteroidsmall.png");

        private Size(int dim, float speed, int points, String texFile) 
        {
            this.dim = dim;
            this.speed = speed;
            this.points = points;
            tex = new Texture(Gdx.files.internal(texFile));
        }
        
        public final int dim;
        public final float speed;
        public final int points;
        public final Texture tex;
    }
    
    public Asteroid(float x, float y, Size size)
    {
        asX = x;
        asY = y;
        
        this.size = size;
        aTexture = size.tex;
        
        asH = size.dim;
        asW = size.dim;
        asMidX = asH / 2;
        asMidY = asW / 2;
        hitMid = asMidX;
        
        direction = (float) (Math.random() * 360);
        asDX = (float) Math.cos(Math.toRadians(direction)) * size.speed;
        asDY = (float) Math.sin(Math.toRadians(direction)) * size.speed;
        rotSpeed = ((float) Math.random() * 3) - ((float) Math.random() * 3);
    }
    
    public Asteroid(float x, float y, Size size, float direction)
    {
        asX = x;
        asY = y;
        
        this.size = size;
        aTexture = size.tex;
        
        
        asH = size.dim;
        asW = size.dim;
        asMidX = asH / 2;
        asMidY = asW / 2;
        hitMid = asMidX;
        
        this.direction = direction;
        asDX = (float) Math.cos(Math.toRadians(direction)) * size.speed;
        asDY = (float) Math.sin(Math.toRadians(direction)) * size.speed;
        rotSpeed = ((float) Math.random() * 3) - ((float) Math.random() * 3);
    }    
    
    @Override
    public void update(GameScreen s) 
    {
        asX += asDX;
        asY += asDY;

        if (asX + asMidX < 0) 
        {
            asX = AsteroidsApp.projectionWidth;
        }

        if (asX > AsteroidsApp.projectionWidth + asMidX) 
        {
            asX = 0;
        }

        if (asY + asMidX < 0) 
        {
            asY = AsteroidsApp.projectionHeight;
        }

        if (asY > AsteroidsApp.projectionHeight + asMidX)
        {
            asY = 0;
        }
        
        rotate();
    }

    @Override
    public void render(SpriteBatch sBatch) 
    {
        sBatch.draw(aTexture, asX, asY, asMidX, asMidY, asW, asH, 
                    1, 1, direction, 0, 0, asW, asH, false, true);
    }
    
    public void rotate()
    {
        if (direction + rotSpeed > 360)
        {
            direction = 360 - direction - rotSpeed;
        }
        else if (direction + rotSpeed < 0)
        {
            direction = rotSpeed - direction;
        }
        else
            direction += rotSpeed;
    }
    
    @Override
    public int getRadius() 
    {
        return (int) hitMid;
    }

    @Override
    public Point getCenter() 
    {
        return new Point((int) (asX + asMidX), (int) (asY + asMidY));
    }
    
    public void setDead()
    {
        dead = true;
    }
    
    public boolean isDead()
    {
        return dead;
    }
    
    public float getX()
    {
        return asX;
    }
    
    public float getY()
    {
        return asY;
    }
    
    public float getDirection()
    {
        return direction;
    }
    
    public String getSize()
    {
        if (size.equals(size.LARGE))
            return "LARGE";
        else if (size.equals(size.MEDIUM))
            return "MEDIUM";
        else
            return "SMALL";
    }
    
    private float asDX;
    private float asDY;
    private float asX;
    private float asY;
    private float asMidX;
    private float asMidY;
    
    private Size size;
    Texture aTexture;
    
    private float direction;
    private float rotSpeed;
    private float hitMid;
    
    private int asW;
    private int asH;
    
    public boolean dead = false;
}