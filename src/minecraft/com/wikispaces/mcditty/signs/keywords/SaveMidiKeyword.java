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
package com.wikispaces.mcditty.signs.keywords;

import net.minecraft.src.BlockSign;

import com.wikispaces.mcditty.signs.ParsedSign;

/**
 * 
 */
public class SaveMidiKeyword extends ParsedKeyword {
	
	private String midiFilename = null;
	
	public SaveMidiKeyword(String rawLine) {
		super(rawLine);
	}

	public static SaveMidiKeyword parse(String rawLine) {
		SaveMidiKeyword keyword = new SaveMidiKeyword(rawLine);
		return keyword;
	}

	@Override
	public boolean isFirstLineOnly() {
		return true;
	}

	@Override
	public <T extends ParsedKeyword> void parseWithMultiline(
			ParsedSign parsedSign, int keywordLine, T k) {
		super.parseWithMultiline(parsedSign, keywordLine, k);
		
		// Mark filename line
				parsedSign.getLines()[1] = this;
		
		String givenFilename = (String) parsedSign.getSignText()[1];
		if (!givenFilename.matches("[\\d\\w]*") && (!givenFilename.equals(""))) {
			// Bad filename: non-alphanumeric characters
			setGoodKeyword(false);
			setErrorMessageType(ERROR);
			setErrorMessage("A MIDI file name should only contain letters and numbers (no spaces)");
			BlockSign.simpleLog("Bad filename: " + givenFilename);
			return;
		} else if (givenFilename.equals("")) {
			// Empty filenames are frowned upon
			setGoodKeyword(false);
			setErrorMessageType(ERROR);
			setErrorMessage("Put a file name on the line after a "
					+ getKeyword() + " keyword.");
			return;
		}

		// Otherwise, good filename
		setMidiFilename(givenFilename + ".mid");
	}

	@Override
	public boolean isMultiline() {
		return true;
	}

	public String getMidiFilename() {
		return midiFilename;
	}

	public void setMidiFilename(String midiFilename) {
		this.midiFilename = midiFilename;
	}
}
