/**
 * Copyright (c) 2012 William Karnavas 
 * All Rights Reserved
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
package com.wikispaces.mcditty;

import net.minecraft.client.Minecraft;

/**
 * This is a substitute for a ModLoader class, which can be used even when
 * ModLoader is not present in the mod. 
 * 
 * IF THERE IS PROPER MODLOADER IN THE MOD, HAVE THIS CLASS EXTEND BASEMOD
 * 
 */
// public class BaseModSubstitute extends BaseMode {
public class BaseModSubstitute {

	public BaseModSubstitute() {
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	public void load() {
		// TODO Auto-generated method stub
		
	}

	public boolean onTickInGame(float f, Minecraft minecraft) {
		// TODO Auto-generated method stub
		return false;
	}

}