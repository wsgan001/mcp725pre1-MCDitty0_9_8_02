package org.jfugue.extras;

import org.jfugue.ParserListener;
import org.jfugue.elements.ChannelPressure;
import org.jfugue.elements.Controller;
import org.jfugue.elements.Instrument;
import org.jfugue.elements.KeySignature;
import org.jfugue.elements.Layer;
import org.jfugue.elements.Lyric;
import org.jfugue.elements.MCDittyEvent;
import org.jfugue.elements.Measure;
import org.jfugue.elements.Note;
import org.jfugue.elements.PitchBend;
import org.jfugue.elements.PolyphonicPressure;
import org.jfugue.elements.SystemExclusive;
import org.jfugue.elements.Tempo;
import org.jfugue.elements.Time;
import org.jfugue.elements.Voice;

/**
 * Used to display the status of a Parser.
 * 
 * For all events, prints the event's verifyString when an event is parsed by the parser. 
 * 
 * Prints output to stdout (uses displayMessage).
 * 
 * TODO I don't know why Eclipse is complaining about overrides in here -Ska
 * 
 * @author David Koelle
 * @version 4.1.0
 *
 */
public class StatusTrackingParserListener implements ParserListener
{
	private String prefix;
	
	/**
	 * Allows instances to prefix output with a particular string.
	 * 
	 * For example, if you're using two StatusTrackingParserListeners,
	 * and you attach one to a Player and one to an Anticipator, you'll
	 * want to know which outputs are coming from which listener. You
	 * could use this method to indicate that the Player's listener should be
	 * prefixed with "PLAYER:", and the Anticipator's with "ANTICIPATOR:".
	 *  
	 * @param prefix
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	/** 
	 * Prints output to stdout (using System.out.println). Intended to be overridden by a subclass
	 * if you have a different place where you'd like to display or log your messages.
	 * 
	 * Methods that override this should remember to display/log prefix text, if it exists.
	 * 
	 * (I could have named this method 'outputMessage', but output isn't often used as a verb, and it 
	 * didn't sound right)
	 *  
	 * @param message The message to display or log
	 */
	protected void sendMessageToOutput(String message) {
		if (prefix != null) {
			System.out.print(prefix);
		}
		System.out.println(message);
	}
	
//	@Override
	public void channelPressureEvent(ChannelPressure channelPressure) {
		sendMessageToOutput(channelPressure.getVerifyString());
	}

//	@Override
	public void controllerEvent(Controller controller) {
		sendMessageToOutput(controller.getVerifyString());
	}

//	@Override
	public void instrumentEvent(Instrument instrument) {
		sendMessageToOutput(instrument.getVerifyString());
	}

//	@Override
	public void keySignatureEvent(KeySignature keySig) {
		sendMessageToOutput(keySig.getVerifyString());
	}

//	@Override
	public void systemExclusiveEvent(SystemExclusive sysex) {
		sendMessageToOutput(sysex.getVerifyString());
    }

//	@Override
	public void layerEvent(Layer layer) {
		sendMessageToOutput(layer.getVerifyString());
	}

//	@Override
	public void measureEvent(Measure measure) {
		sendMessageToOutput(measure.getVerifyString());
	}

//	@Override
	public void noteEvent(Note note) {
		sendMessageToOutput(note.getVerifyString());
	}

//	@Override
	public void parallelNoteEvent(Note note) {
		sendMessageToOutput(note.getVerifyString());
	}

//	@Override
	public void pitchBendEvent(PitchBend pitchBend) {
		sendMessageToOutput(pitchBend.getVerifyString());
	}

//	@Override
	public void polyphonicPressureEvent(PolyphonicPressure polyphonicPressure) {
		sendMessageToOutput(polyphonicPressure.getVerifyString());
	}

//	@Override
	public void sequentialNoteEvent(Note note) {
		sendMessageToOutput(note.getVerifyString());
	}

//	@Override
	public void tempoEvent(Tempo tempo) {
		sendMessageToOutput(tempo.getVerifyString());
	}

//	@Override
	public void timeEvent(Time time) {
		sendMessageToOutput(time.getVerifyString());
	}

//	@Override
	public void voiceEvent(Voice voice) {
		sendMessageToOutput(voice.getVerifyString());
	}

	// MCDitty
	@Override
	public void lyricEvent(Lyric event) {
		sendMessageToOutput(event.getVerifyString());
	}

	// MCDitty
	@Override
	public void mcDittyEvent(MCDittyEvent event) {
		sendMessageToOutput(event.getVerifyString());
	}
}
