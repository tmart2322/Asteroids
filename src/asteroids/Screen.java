package asteroids;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Screen
{
	public void init();

	public boolean onKeyDown(int keyCode);
	public boolean onKeyUp(int keyCode);
	public void update(int width, int height);
	public void render(SpriteBatch sBatch);

	public Screen getNextScreen();
}
