package asteroids;

import asteroids.Asteroid.Size;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

public class GameScreen implements Screen
{

    public GameScreen() 
    {
    }
	@Override
	public void init()
	{
            s = new Ship();
            createAsteroids();
            if (fontScore == null) 
            {
                FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                        Gdx.files.internal(fontFile));
                FreeTypeFontGenerator.FreeTypeFontParameter param =
                        new FreeTypeFontGenerator.FreeTypeFontParameter();
                param.size = 24;
                param.characters = "0123456789Score:";
                param.flip = true;
                fontScore = gen.generateFont(param);
                gen.dispose();
            }
            
            if (fontRound == null)
            {
                FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
                        Gdx.files.internal(fontFile));
                FreeTypeFontGenerator.FreeTypeFontParameter param =
                        new FreeTypeFontGenerator.FreeTypeFontParameter();
                param.size = 24;
                param.characters = "0123456789Round:";
                param.flip = true;
                fontRound = gen.generateFont(param);
                gen.dispose();
            }
	}

        //If the keys are pressed down it sets their value to active so that it
        //can be processed in the key up
	@Override
	public boolean onKeyDown(int keyCode)
	{
		if (keyCode == Input.Keys.RIGHT)
                   rightActive = true;
                if (keyCode == Input.Keys.LEFT)
                   leftActive = true;
                if (keyCode == Input.Keys.UP)
                    upActive = true;
                if (keyCode == Input.Keys.DOWN)
                    downActive = true;
                if (spawnProt == 0)
                {
                    if (keyCode == Input.Keys.SPACE)
                        spaceActive = true;
                    if (keyCode == Input.Keys.R)
                        rActive = true;
                }
                
                return true;
	}

	@Override
	public boolean onKeyUp(int keyCode)
	{
		if (keyCode == Input.Keys.RIGHT)
                {
                    rightActive = false;
                }
                if (keyCode == Input.Keys.LEFT)
                {
                    leftActive = false;
                }
                if (keyCode == Input.Keys.UP)
                {
                    upActive = false;
                }
                if (keyCode == Input.Keys.DOWN)
                {
                    downActive = false;
                }
                
                return true;
	}

        //Updates the Gamescreen
	public void update(int width, int height)
	{       
            updateShip();
            updateShot();
            updateAsteroid();
            hitDetection();
            if (asteroidList.isEmpty())
            {
                rounds++;
                aCount++;
                createAsteroids();
                resetShip();
            }
            
            spaceActive = false;
            rActive = false;
	}
        
        private void hitDetection() 
        {
            //Shot hits asteroid
            for (int a = 0; a < asteroidList.size(); a++)
            {
                Asteroid as = asteroidList.get(a);
                for (int s = 0; s < shotList.size(); s++)
                {
                    Shot ss = shotList.get(s);
                    
                    if (!(as.isDead()) && !(ss.isDead()))
                    {
                        Point aPt = as.getCenter(); 
                        Point sPt = ss.getCenter();
                        
                        if (aPt.distance(sPt) <= as.getRadius() + ss.getRadius())
                        {
                            as.setDead();
                            ss.setDead();
                        }
                    }
                }
            }
            
            if (spawnProt == 0 && !invincible) 
            {
                //Asteroid hits ship
                for (int a = 0; a < asteroidList.size(); a++) 
                {
                    Asteroid as = asteroidList.get(a);

                    if (!(as.isDead()) && !(s.isDead())) 
                    {
                        Point aPt = as.getCenter();
                        Point sPt = s.getCenter();

                        if (aPt.distance(sPt) <= as.getRadius() + s.getRadius()) 
                        {
                            as.setDead();
                            s.setDead();
                        }
                    }
                }
            }
        }
        
        //Renders the object in the game window
	@Override
	public void render(SpriteBatch sBatch)
	{
            if (spawnProt == 0)
                s.render(sBatch);
            else
            {
                if (spawnProt % 10 == 0)
                    toggleFlash = !toggleFlash;
                if (toggleFlash)
                    s.render(sBatch);
                spawnProt--;
            }
            
            for (Shot s: shotList)
                s.render(sBatch);
            for (Asteroid a: asteroidList)
                a.render(sBatch);
            
            int x = 15;
            int y = 15;
            Texture sTexture = new Texture(Gdx.files.internal("src/asteroids/images/ship.png"));
            for (int i = 0; i < shipLives; i++)
            {
                sBatch.draw(sTexture, x, y, 15, 15, 30, 30, 
                        1, 1, -90, 0, 0, 30, 30, false, true);
                x += 50;
            }
            
            fontScore.draw(sBatch, "Score: " + s.getScore(), 180 , 20);
            fontRound.draw(sBatch, "Round: " + Integer.toString(rounds), 500, 20);
	}
        
        //True if the left key is pressed down
        public boolean isLeft()
        {
            return leftActive;
        }
        
        //True if the right key is pressed down
        public boolean isRight()
        {
            return rightActive;
        }

        //True if the down key is pressed down
        public boolean isDown()
        {
            return downActive;
        }
        
        //True if the up key is pressed down
        public boolean isUp()
        {
            return upActive;
        }
        
        //True if the space key is pressed down
        public boolean isSpace()
        {
            return spaceActive;
        }
        
        public boolean isR()
        {
            return rActive;
        }
        
        //Adds a shot to the shot list
        public void addShot(float x, float y, float direction)
        {
            shotList.add(new Shot(x, y, direction));
        }
        
        //Updates the shot: if the shot's state isDead(), the shot is removed
        //from the list. Otherwise, it is updated
        public void updateShot()
        {
            if (!shotList.isEmpty())
            {
                Iterator<Shot> iter = shotList.iterator();
                while (iter.hasNext())
                {
                    Shot s = iter.next();
                    if (s.isDead())
                    {
                        iter.remove();
                    }
                    else
                        s.update(this);
                }
            }
        }
        
        //Updates the asteroids
        public void updateAsteroid()
        {
            //Makes the asteroid smaller if it is hit and removes it if it is
            //size small
            for (int i = 0; i < asteroidList.size(); i++)
            {
                Asteroid a = asteroidList.get(i);
                if (a.isDead())
                {
                    if (a.getSize() == "LARGE")
                    {
                        s.addScore(Size.LARGE.points);
                        asteroidList.add(new Asteroid(a.getX(), a.getY(), Size.MEDIUM, 
                                a.getDirection() + (float) (Math.random() * deadRot)));
                        asteroidList.add(new Asteroid(a.getX(), a.getY(), Size.MEDIUM, 
                                a.getDirection() - (float) (Math.random() * deadRot)));
                    }
                    else if (a.getSize() == "MEDIUM")
                    {
                        s.addScore(Size.MEDIUM.points);
                        asteroidList.add(new Asteroid(a.getX(), a.getY(),Size.SMALL, 
                                a.getDirection() + (float) (Math.random() * deadRot)));
                        asteroidList.add(new Asteroid(a.getX(), a.getY(),Size.SMALL, 
                                a.getDirection() - (float) (Math.random() * deadRot)));
                    }
                    else
                    {
                        s.addScore(Size.SMALL.points);
                        a.setDead();
                    }
                }
            }
            
            //Removes any dead asteroids from the list
            if (!asteroidList.isEmpty())
            {
                Iterator<Asteroid> iter = asteroidList.iterator();
                while (iter.hasNext())
                {
                    Asteroid a = iter.next();
                    if (a.isDead())
                    {
                        iter.remove();
                    }
                    else
                        a.update(this);
                }
            }
        }
        
        //Updates the ships
        public void updateShip()
        {
                if (s.isDead() && shipLives >= 0)
                {
                    shipLives--;
                    resetShip();
                    spawnProt = 100;
                }
                
                if (shipLives >= 0)
                {
                    s.update(this);
                }
        }
        
        public void resetShip()
        {
            s.setNotDead();
            s.setX(540);
            s.setY(300);
            s.setDirection(270);
        }
        
        //Creates the asteroids at the start of each mission
        public void createAsteroids()
        {
            for (int i = 0; i < aCount; i++)
            {
                float height = 800;
                float width = 1280;
                
                int shipX = (int) s.getX();
                int shipY = (int) s.getY();
                Point shipP = new Point(shipX, shipY);
                
                Point startP = new Point((int) (Math.random() * width), 
                        (int) (Math.random() * height));
                
                while(shipP.distance(startP) < 350)
                {
                    startP = new Point((int) (Math.random() * width), 
                            (int) (Math.random() * height));
                }
                
                asteroidList.add(new Asteroid(startP.x, startP.y, Size.LARGE));
            }
        }
        
	@Override
	public Screen getNextScreen()
	{
		return null;
	}
        
        ArrayList<Shot> shotList = new ArrayList<>();
        ArrayList<Asteroid> asteroidList = new ArrayList<>();
        Ship s;
        
        private boolean upActive = false;
        private boolean downActive = false;
        private boolean rightActive = false;
        private boolean leftActive = false;
        private boolean spaceActive = false;
        private boolean rActive = false;
        
        private static int deadRot = 30;
        
        private int aCount = 3;
        private int shipLives = 3;
        private boolean toggleFlash = false;
        private int spawnProt = 0;
        private boolean invincible = false;
        private int rounds = 1;
        BitmapFont fontScore;
        BitmapFont fontRound;
        String fontFile = "src/asteroids/images/Arial.ttf";
}