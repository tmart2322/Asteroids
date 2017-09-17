package asteroids;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class AsteroidsApp implements ApplicationListener
{
	public static final int playWidth = 800;
	public static final int playHeight = 600;

	public static final int projectionHeight = playHeight;
	public static int projectionWidth; 

	private static final int frameRate = 60;
	private static final long nanosPerUpdate = 1000000000 / frameRate;

	private Screen currScreen;
	private OrthographicCamera camera;
	private SpriteBatch sBatch;
	private long nextUpdateTime;

	@Override
	public void create()
	{
		currScreen = new GameScreen();
		currScreen.init();

		sBatch = new SpriteBatch();

		InputProcessor ip = new InputAdapter()
		{
			@Override
			public boolean keyDown(int keycode)
			{
				return currScreen.onKeyDown(keycode);
			}

			@Override
			public boolean keyUp(int keyCode)
			{
				return currScreen.onKeyUp(keyCode);
			}
		};
		Gdx.input.setInputProcessor(ip);

		nextUpdateTime = System.nanoTime() + nanosPerUpdate;
	}

	@Override
	public void resize(int width, int height)
	{
		// Compute the correct projection width to maintain a constant height,
		// but match the aspect ratio of the window
		projectionWidth = width * projectionHeight / height;
		camera = new OrthographicCamera();
		camera.setToOrtho(true, projectionWidth, projectionHeight);
	}

	@Override
	public void render()
	{
		// Keep a constant update rate regardless of render rate
		while (System.nanoTime() - nextUpdateTime > 0)
		{
			currScreen.update(projectionWidth, projectionHeight);
			// Is it time to switch screens yet?
			Screen nextScreen = currScreen.getNextScreen();
			if (nextScreen != null)
			{
				nextScreen.init();
				currScreen = nextScreen;
			}

			// Update desired time for next frame
			nextUpdateTime += nanosPerUpdate;
		}

		// Now draw.  First fill the background...
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Then have the screen do the actual drawing
		camera.update();
		sBatch.setProjectionMatrix(camera.combined);
		sBatch.begin();
		currScreen.render(sBatch);
		sBatch.end();
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void dispose()
	{
	}
}