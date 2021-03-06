package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityPlayer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TileLadder extends Tile
{
	private boolean down;
	private int floor;
	
	public TileLadder(boolean goesDown, int currentFloor)
	{
		down = goesDown;
		floor = currentFloor;
	}
	
	@Override
	public boolean onWalkAttempt(Entity entity)
	{
		Game.message("Use the ladder to go "+(down?"down":"up")+" a level!");
		return false;
	}

	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.draw(Game.tileTex, Game.xOffset+x*32, Game.yOffset+y*32, 32, 32, 0, down?32:64, 32, 32, false, false);
	}
	
	@Override
	public boolean setCurrentEntity(Entity entity)
	{
		return false;
	}
	
	@Override
	public Entity getCurrentEntity()
	{
		return null;
	}
	
	@Override
	public boolean givesLight()
	{
		return true;
	}
	
	@Override
	public boolean takesLight()
	{
		return true;
	}
	
	@Override
	public void use(Entity entity)
	{
		if(entity instanceof EntityPlayer)
		{
			if(Game.addEntity(entity, Game.maps[floor+(down?1:-1)], true)) Game.curMap = entity.map;
		}
	}
}