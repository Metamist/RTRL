package org.tilegames.hexicube.topdownproto.entity;

import org.tilegames.hexicube.topdownproto.FontHolder;
import org.tilegames.hexicube.topdownproto.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class EntityDamageHealthDisplay extends Entity
{
	private boolean damage;
	private long amount;
	
	private int renderOffset;
	
	private short timeLived;
	
	public EntityDamageHealthDisplay(boolean damage, long amount, int x, int y)
	{
		this.damage = damage;
		this.amount = amount;
		timeLived = 0;
		xPos = x;
		yPos = y;
		width = 0;
		height = 0;
		renderOffset = 0;
	}
	
	@Override
	public void tick()
	{
		timeLived++;
		if(timeLived >= 60) Game.removeEntity(this);
		else renderOffset++;
	}
	@Override
	public void render(SpriteBatch batch, int camX, int camY)
	{
		if(damage) batch.setColor(1, 0, 0, (float)(60-timeLived)/60);
		else batch.setColor(0, 1, 0, (float)(60-timeLived)/60);
		char[] data = FontHolder.getCharList(Game.numToStr((int)amount, 0, "-"));
		FontHolder.render(batch, data, (int)(xPos*32)-camX*32-FontHolder.getTextWidth(data, false)/2+16, (int)(yPos*32)+renderOffset-camY*32+24, false);
	}
	@Override
	public void collide(Entity entity){}
}