package org.tilegames.hexicube.topdownproto.map;

import org.tilegames.hexicube.topdownproto.entity.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Tile
{
	public abstract boolean onWalkAttempt(Entity entity);
	public abstract void render(SpriteBatch batch, int x, int y);
	public abstract void setCurrentEntity(Entity entity);
	public abstract Entity getCurrentEntity();
	public abstract boolean lightable();
	public abstract void use(Entity entity);
	
	public int[] lightLevel = new int[3];
	public int[] lightSource = new int[3];
}