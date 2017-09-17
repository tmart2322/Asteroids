package asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.awt.Point;

public class Ship implements GameObjects
{
        //Creates a ship in the center of the game
        public Ship()
        {
            if (sTexture == null)
                sTexture = new Texture(Gdx.files.internal(shipImage));

		shipX = 540;
		shipY = 300;
                direction = -90;
        }
        
        //Creates a ship with a starting position at the given parameters
        public Ship(float startX, float startY)
        {
            if (sTexture == null)
                sTexture = new Texture(Gdx.files.internal(shipImage));

            shipX = startX;
            shipY = startY; 
            direction = -90;
        }
        
        @Override
        public void update(GameScreen s) 
        {
            shipX += shipDX;
            shipY += shipDY;
            
            shipDX = shipDX * friction;
            shipDY = shipDY * friction;
            
            if (s.isDown())
            {
                speedDown();
            }
            
            if (s.isUp())
            {
                speedUp();
            }
            
            if (s.isLeft())
            {
                rotateLeft();
            }

            if (s.isRight())
            {
               rotateRight();
            }
            
            if (s.isR())
            {
                randomLocation();
            }
            
            if (s.isSpace())
            {
                s.addShot(shipX + 15, shipY + 15, direction);
            }
            
            if (shipX < 0) 
            {
                shipX = AsteroidsApp.projectionWidth;
            }

            if (shipX > AsteroidsApp.projectionWidth) 
            {
                shipX = 0;
            }

            if (shipY < 0) 
            {
                shipY = AsteroidsApp.projectionHeight;
            }

            if (shipY > AsteroidsApp.projectionHeight) 
            {
                shipY = 0;
            }

        }

        @Override
        public void render(SpriteBatch sBatch) 
        {
            sBatch.draw(sTexture, shipX, shipY, shipMidX, shipMidY, shipW, shipH, 
                    1, 1, direction, 0, 0, shipW, shipH, false, true);
        }
        
        //Accelerates the ship's speed 
        public void speedUp()
        {
            shipDX += Math.cos(Math.toRadians(direction)) * speedInc;
            shipDY += Math.sin(Math.toRadians(direction)) * speedInc;
        }
        
        //Appies friction to the ships speed
        public void speedDown()
        {
           shipDX = shipDX * friction;
           shipDY = shipDY * friction;
        }
        
        //Rotates the ship to the left
        public void rotateLeft()
        {
            if (direction > rotSpeed)
                direction -= rotSpeed;
            else
                direction = 360 + (direction - rotSpeed);
        }
        
        //Rotates the ship to the
        public void rotateRight()
        {
           if ((direction + rotSpeed) < 360)
               direction += rotSpeed;
           else
               direction = (rotSpeed + direction) - 360;
        }
        
        public void randomLocation()
        {
            shipX = (float) (Math.random() * AsteroidsApp.projectionWidth);
            shipY = (float) (Math.random() * AsteroidsApp.projectionHeight);
        }
        
        public float getX()
        {
            return shipX;
        }
        
        public float getY()
        {
            return shipY;
        }
        
        public void setX(float x)
        {
            shipX = x;
        }
        
        public void setY(float y)
        {
            shipY = y;
        }
        
        public void setDirection(float dir)
        {
            direction = dir;
        }
        
        @Override
        public int getRadius()
        {
            return (int) hitMid;
        }
        
        @Override
        public Point getCenter()
        {
            return new Point((int) (shipX + shipMidX), (int) (shipY + shipMidY));
        }
        
        public void setDead()
        {
            dead = true;
        }
        
        public void setNotDead()
        {
            dead = false;
        }
        
        public boolean isDead()
        {
            return dead;
        }
        
        public void addScore(int i)
        {
            shipScore += i;
        }
        
        public int getScore()
        {
            return shipScore;
        }
        
        private static final String shipImage = "src/asteroids/images/ship.png";
        private static final String shipSound = null;
	private static final int shipW = 30;
	private static final int shipH = 30;

	private static Texture sTexture;
        private static Sound sSound;
        
        //Location of the ship
	private float shipX;
	private float shipY;
        //Speed of the ship
	private float shipDX;
	private float shipDY;
        //Middle of the ship
        private float shipMidX = shipW / 2;
        private float shipMidY = shipH / 2;
        private float hitMid = shipMidX;
        
        private static final float speedInc = .05f;
        private static final float friction = .995f;
        
        private static final float rotSpeed =  5f;
        private float direction;
        private int shipScore = 0;
        
        private boolean dead = false;
}