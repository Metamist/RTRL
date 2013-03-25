package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.Game;
import org.tilegames.hexicube.topdownproto.entity.DamageType;
import org.tilegames.hexicube.topdownproto.entity.Direction;
import org.tilegames.hexicube.topdownproto.entity.Entity;
import org.tilegames.hexicube.topdownproto.entity.EntityLiving;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ItemWeaponBadSword extends ItemWeapon
{
	private static boolean nameDiscovered = false;
	private boolean modDiscovered;
	
	public static Texture tex;
	
	private int durability;
	private ItemModifier modifier;
	
	public ItemWeaponBadSword()
	{
		int rand = Game.rand.nextInt(10);
		if(rand < 2) modifier = ItemModifier.SHODDY;
		else if(rand < 6) modifier = ItemModifier.NONE;
		else if(rand < 9) modifier = ItemModifier.CURSED;
		else modifier = ItemModifier.SHARPENED;
		
		durability = getMaxDurability();
		
		nameDiscovered = false;
		modDiscovered = false;
	}
	
	public ItemWeaponBadSword(ItemModifier mod, int dur)
	{
		modifier = mod;
		durability = dur;
		
		nameDiscovered = false;
		modDiscovered = false;
	}
	
	@Override
	public boolean use(Entity source, Direction dir)
	{
		Entity target = null;
		if(dir == Direction.NONE) return false;
		else if(dir == Direction.UP) target = source.map.tiles[source.xPos][source.yPos+1].getCurrentEntity();
		else if(dir == Direction.DOWN) target = source.map.tiles[source.xPos][source.yPos-1].getCurrentEntity();
		else if(dir == Direction.LEFT) target = source.map.tiles[source.xPos-1][source.yPos].getCurrentEntity();
		else if(dir == Direction.RIGHT) target = source.map.tiles[source.xPos+1][source.yPos].getCurrentEntity();
		else return false;
		if(target == null) return false;
		if(!(target instanceof EntityLiving)) return false;
		if(durability <= 0) return false;
		if(!modDiscovered)
		{
			Game.message("You realise the Badsword is "+(modifier==ItemModifier.SHODDY?"shoddy":"sharp")+"...");
			modDiscovered = true;
		}
		EntityLiving e = (EntityLiving)target;
		if(!e.alive) return false;
		e.hurt((modifier==ItemModifier.SHARPENED?Game.rollDice(2, 4):(Game.rollDice(4, 1)-(modifier==ItemModifier.SHODDY?1:0))), DamageType.SHARP);
		durability--;
		return true;
	}
	@Override
	public String getWeaponDamageRange()
	{
		if(!nameDiscovered) return "???";
		if(!modDiscovered) return "1d4";
		if(modifier == ItemModifier.SHARPENED) return "2d4";
		if(modifier == ItemModifier.SHODDY) return "1d4-1";
		return "1d4";
	}
	@Override
	public int getMaxDurability()
	{
		return 200;
	}

	@Override
	public int getCurrentDurability()
	{
		return durability;
	}

	@Override
	public DamageType getAttackType()
	{
		return DamageType.SHARP;
	}
	@Override
	public ItemModifier getModifier()
	{
		return modifier;
	}
	@Override
	public String getName()
	{
		if(modifier == null || modifier == ItemModifier.NONE) modDiscovered = true; 
		if(!nameDiscovered)
		{
			if(modDiscovered) return "Unknown Sword";
			return "Unusual Sword";
		}
		if(!modDiscovered) return "Unusual Badsword";
		if(modifier == null || modifier == ItemModifier.NONE) return "Badsword";
		if(modifier == ItemModifier.CURSED) return "Cursed Badsword";
		if(modifier == ItemModifier.SHARPENED) return "Sharpened Badsword";
		if(modifier == ItemModifier.SHODDY) return "Really Badsword";
		System.out.println("Removed bad modifier on badsword: "+modifier);
		modifier = ItemModifier.NONE;
		return "Badsword";
	}
	@Override
	public void tick(Entity entity, boolean equipped)
	{
		if(equipped)
		{
			if(!nameDiscovered) Game.message("Discovered item: Badsword");
			nameDiscovered = true;
			if(modifier == ItemModifier.CURSED && !modDiscovered)
			{
				Game.message("The Badsword is cursed, you can't remove it!");
				modDiscovered = true;
			}
		}
	}
	@Override
	public boolean canMove()
	{
		return !(modifier == ItemModifier.CURSED);
	}

	@Override
	public void render(SpriteBatch batch, int x, int y)
	{
		batch.draw(tex, x, y);
	}
}