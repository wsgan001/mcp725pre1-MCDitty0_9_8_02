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

import java.io.File;

import org.jfugue.elements.Note;

import com.wikispaces.mcditty.config.MCDittyConfig;
import com.wikispaces.mcditty.sfx.SFXManager;
import com.wikispaces.mcditty.signs.ParsedSign;

/**
 * 
 *
 */
public class SFXInstKeyword extends ParsedKeyword {

	// /**
	// * A MusicStringParser, set once and used many times thereafter to save
	// time.
	// */
	// private static MusicStringParser musicStringParser = new
	// MusicStringParser();

	/**
	 * The instrument number to replace with a sound effect
	 */
	private int instrument;

	/**
	 * The filename of the sound effect indicated
	 */
	private String sfxFilename;

	/**
	 * The sfx shorthand
	 */
	private String sfxName;
	
	/**
	 * The sfx shorthand, even if it is not yet a valid name
	 */
	private String sfxNameIncomplete;

	/**
	 * SFX number
	 */
	private int sfxNumber = 1;

	/**
	 * The pitch the sound effect is "centered at": A sound with a center pitch
	 * of C5 will play normally to make a C5, higher for a D5, lower for a B4,
	 * etc.
	 * 
	 * -1 if undefined
	 * 
	 * In semitones, identical to JFugue's system.
	 */
	private int centerPitch = -1;

	/**
	 * @param wholeKeyword
	 */
	public SFXInstKeyword(String wholeKeyword) {
		super(wholeKeyword);
	}

	public static SFXInstKeyword parse(String rawArgs) {
		SFXInstKeyword keyword = new SFXInstKeyword(rawArgs);

		// Parse the first line of a SFX inst sign

		// Get the instrument number
		String[] args = rawArgs.split(" ");
		int numArgs = args.length;

		if (numArgs > 2) {
			// Too many arguments
			keyword.setGoodKeyword(true);
			keyword.setErrorMessageType(INFO);
			keyword.setErrorMessage("Only the instrument number is needed on the first line.");
		} else if (numArgs <= 1) {
			// No instrument number
			keyword.setGoodKeyword(false);
			keyword.setErrorMessageType(ERROR);
			keyword.setErrorMessage("Follow SFXInst with an instrument number.");
			return keyword;
		} else {
			// Instrument number given
			String argument = args[1];
			if (argument.trim().matches("\\d+")) {
				keyword.setInstrument(Integer.parseInt(argument));
			} else {
				// Error: invalid agument
				keyword.setGoodKeyword(false);
				keyword.setErrorMessageType(ERROR);
				keyword.setErrorMessage("Follow SFXInst with an instrument number.");
				return keyword;
			}

			if (keyword.getInstrument() < 0 || keyword.getInstrument() > 127) {
				// Out of bounds
				keyword.setInstrument(0);
				keyword.setGoodKeyword(false);
				keyword.setErrorMessageType(ERROR);
				keyword.setErrorMessage("Instrument numbers range from 0 to 127.");
				return keyword;
			}
		}

		return keyword;
	}

	@Override
	public boolean isFirstLineOnly() {
		return false;
	}

	@Override
	public boolean isMultiline() {
		return true;
	}

	@Override
	public <T extends ParsedKeyword> void parseWithMultiline(
			ParsedSign parsedSign, int keywordLine, T k) {
		super.parseWithMultiline(parsedSign, keywordLine, k);

		// Pass along errors if they exist
		if (!k.isGoodKeyword()) {
			return;
		}

		// Mark first line as keyword
		parsedSign.getLines()[keywordLine] = this;

		// Check for a second line, and mark it
		if (keywordLine + 1 < parsedSign.getLines().length) {
			parsedSign.getLines()[keywordLine + 1] = this;
		} else {
			// Too little space; give warning
			setGoodKeyword(false);
			setErrorMessageType(WARNING);
			setErrorMessage("Move this keyword up -- there isn't enough room for the second line.");
			return;
		}

		// Parse second line
		// Split second line into tokens
		String[] secondLineTokens = parsedSign.getSignText()[keywordLine + 1]
				.toString().toLowerCase().split("\\s\\s*");

		// Process each token
		for (String s : secondLineTokens) {
			// Check for a number (center pitch)
			if (s.matches("\\d+")) {
				// Center pitch
				setCenterPitch(Integer.parseInt(s));
			} else if (s.matches("[abcdefg][#b]*[\\d]?")) {
				// Check for a note (C#3, D, G5)
				// If found, parse it
				Note note = Note.createNote(s);
				// Save the pitch value
				setCenterPitch(note.getValue());
			} else {
				// Check for a SFX name, or throw an error it is not one
				// Also handle a number appended to the SFX name
				
				// Always set the incomplete sfx name, whatever else betide
				setSFXNameIncomplete(s.replaceAll("\\d", ""));

				// Check for valid SFX name, stripping any digits first.
				// XXX: Could cause bugs by removing digits in middle of
				// shorthand
				String fullSFXName = SFXManager.getEffectForShorthandName(s
						.replaceAll("\\d", ""));

				if (fullSFXName == null) {
					// No SFX by that name.
					if (s.replaceAll("\\d", "").length() <= 0) {
						// No SFX ever given
						setGoodKeyword(false);
						setErrorMessage("Put a SFX name on the second line of a SFXInst.");
						setErrorMessageType(ERROR);
						return;
					} else {
						setGoodKeyword(true);
						setErrorMessage("The sound effect "
								+ s.replaceAll("\\d", "")
								+ " does not exist; will be slient.");
						setErrorMessageType(WARNING);
					}
					return;
				} else {
					// SFX is valid; set up

					// Decide what sound effect number to ask for, based on the
					// digit or absence of a digit at the end of the sfx name
					char digitChar = s.charAt(s.length() - 1);
					String digitString = new StringBuilder().append(digitChar)
							.toString();

					int digit = 1;
					if (digitString.matches("[1234567890]")) {
						// Last character is a digit!
						digit = Integer.parseInt(digitString);
					}

					// Save the SFX number
					setSFXNumber(digit);

					// Get the filename of the sfx
					File sfxFile = SFXManager.getEffectFile(fullSFXName, digit);
					if (!sfxFile.exists()) {
						// If the sfx filename does not exist
						// Try resetting the digit to 0
						sfxFile = SFXManager.getEffectFile(fullSFXName, 1);
						// Update the saved SFX number to reflect this
						setSFXNumber(1);
					}
					setSFXFilename(sfxFile.getPath());
				}

				// Save original SFX name
				setSFXName(s.replaceAll("\\d", ""));
				
				// Check that the SFX is not an out-of-order SFX
				if (SFXManager.isShorthandOnSFXInstBlacklist(sfxName)) {
					// blacklisted as out of order
					setGoodKeyword(false);
					setErrorMessageType(ERROR);
					setErrorMessage("SFXInst: "+sfxName+" is out of order in "+MCDittyConfig.CURRENT_VERSION+". Try another SFX instead.");
					return;
				}
			}
		}

		// Check that everything is set, and mark the keyword as bad if
		// some are missing
		if (sfxName == null) {
			setGoodKeyword(false);
			setErrorMessageType(ERROR);
			setErrorMessage("Put a SFX name on the second line of a SFXInst keyword.");
			return;
		}

		// TODO: Set default center pitch if not explicitly given
		// (-1 means undefined, set as the default value for field)
		if (centerPitch == -1) {
			// Try to get the default center pitch
			Integer defaultCenterPitch = SFXManager.getDefaultTuningInt(
					SFXManager.getEffectForShorthandName(sfxName), sfxNumber);
			if (defaultCenterPitch != null) {
				setCenterPitch(defaultCenterPitch);
			} else {
				// No center pitch default given
				setGoodKeyword(true);
				setErrorMessageType(WARNING);
				setErrorMessage("No default pitch is given for this SFX; specify your own or C5 will be used.");
				setCenterPitch(60);
			}
		}

		return;
	}

	public int getInstrument() {
		return instrument;
	}

	public void setInstrument(int instrument) {
		this.instrument = instrument;
	}

	public String getSFXFilename() {
		return sfxFilename;
	}

	public void setSFXFilename(String sfxName) {
		this.sfxFilename = sfxName;
	}

	public int getCenterPitch() {
		return centerPitch;
	}

	public void setCenterPitch(int centerPitch) {
		this.centerPitch = centerPitch;
	}

	public String getSfxFilename() {
		return sfxFilename;
	}

	public void setSfxFilename(String sfxFilename) {
		this.sfxFilename = sfxFilename;
	}

	public String getSFXName() {
		return sfxName;
	}

	public void setSFXName(String sfxName) {
		this.sfxName = sfxName;
	}

	public int getSFXNumber() {
		return sfxNumber;
	}

	public void setSFXNumber(int sfxNumber) {
		this.sfxNumber = sfxNumber;
	}

	public String getSFXNameIncomplete() {
		return sfxNameIncomplete;
	}

	public void setSFXNameIncomplete(String sfxNameIncomplete) {
		this.sfxNameIncomplete = sfxNameIncomplete;
	}

}
