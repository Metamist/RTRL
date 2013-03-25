package org.tilegames.hexicube.topdownproto.item;

import org.tilegames.hexicube.topdownproto.entity.Entity;

public abstract class ItemUsable extends Item
{
	public abstract boolean use(Entity source, Entity target);
}