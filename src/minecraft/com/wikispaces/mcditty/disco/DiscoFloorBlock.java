/**
 * Copyright (c) 2012 William Karnavas 
 * All Rights Reserved
 */

/**
 * 
 * This file is part of MCDitty.
 * 
 * MCDitty is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * MCDitty is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with MCDitty. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package com.wikispaces.mcditty.disco;

import com.wikispaces.mcditty.Point3D;


/**
 * @author William
 *
 */
public class DiscoFloorBlock extends Point3D {
	
	int originalBlockMeta = 0;

	public DiscoFloorBlock(Point3D currBlock, int originalBlockMetadata) {
		super (currBlock);
		originalBlockMeta = originalBlockMetadata;
	}

	/**
	 * @return the originalBlockMeta
	 */
	public int getOriginalBlockMeta() {
		return originalBlockMeta;
	}

	/**
	 * @param originalBlockMeta the originalBlockMeta to set
	 */
	public void setOriginalBlockMeta(int originalBlockMeta) {
		this.originalBlockMeta = originalBlockMeta;
	}
	
}