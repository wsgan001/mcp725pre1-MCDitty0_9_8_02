/**
 * 
 * Copyright (c) 2012 William Karnavas All Rights Reserved
 * 
 */

/**
 * 
 * This file is part of MCDitty.
 * 
 * MCDitty is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * 
 * MCDitty is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with MCDitty. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.wikispaces.mcditty.bot;

import net.minecraft.src.EntityVillager;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.World;

public class EntityVillagerBot extends EntityVillager {

	public EntityVillagerBot(World par1World) {
		super(par1World);
		// TODO Auto-generated constructor stub
	}

	public EntityVillagerBot(World par1World, int par2) {
		super(par1World, par2);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.minecraft.src.EntityVillager#isAIEnabled()
	 */
	@Override
	public boolean isAIEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.src.EntityLiving#onEntityUpdate()
	 */
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
	}

}
