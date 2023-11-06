import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.sound.midi.*;
import javax.swing.*;

/**
 * Implements a key on a simulated piano keyboard.
 */
public class Key {
	// You are free to add more instance variables if you wish.
	private Polygon _polygon;
	private int _pitch;
	private boolean _isOn;
	private Piano _piano;
	private String _currentKeyColor = "White";
	private String _priorKeyColor;

	/**
	 * Returns the polygon associated with this key.
	 * @return the polygon associated with this key.
	 */
	public Polygon getPolygon () {
		return _polygon;
	}

	public Key (Polygon polygon, int pitch, Piano piano) {
		_polygon = polygon;
		_pitch = pitch;
		_piano = piano;
	}

	public void play (boolean isOn) {
		try {
			// Some MIDI technicalities; don't worry too much about it.
			ShortMessage myMsg = new ShortMessage();
			final int VELOCITY = 93;
			myMsg.setMessage(isOn ? ShortMessage.NOTE_ON : ShortMessage.NOTE_OFF, 0, _pitch, VELOCITY);
			final int IMMEDIATELY = -1;
			// Send the message to the receiver (either local or remote).
			_piano.getReceiver().send(myMsg, IMMEDIATELY);
			// Set the key to "on".
			_isOn = isOn;
			// Ask the piano to redraw itself (since one of its keys has changed).
			_piano.repaint();
		} catch (InvalidMidiDataException imde) {
			System.out.println("Could not play key!");
		}
	}

	/**
	 * Paints the key using the specified Swing Graphics object.
	 * @param g the Graphics object to be used for painting.
	 */
	public void paint (Graphics g) {
		if (_currentKeyColor=="Black") {
			g.setColor(Color.BLACK);
			g.fillPolygon(_polygon);
			g.drawPolygon(_polygon);
		}
		else {
		g.setColor(Color.WHITE);
		g.fillPolygon(_polygon);
		g.setColor(Color.BLACK);
		g.drawPolygon(_polygon);
	}
		if (_currentKeyColor == "Gray") {
			g.setColor(Color.GRAY);
			g.fillPolygon(_polygon);
			g.setColor(Color.BLACK);
			g.drawPolygon(_polygon);
		}
	}
	
	/*changes color to black*/
	public void blackA () {
		_currentKeyColor = "Black";
	}
	
	/*changes color to white*/
	public void whiteA () {
		_currentKeyColor = "White";
	}
	
	/* changes color to gray while storing original color for when key is released*/
	public void keyPressed () {
		_priorKeyColor = _currentKeyColor;
		_currentKeyColor = "Gray";
	}
	
	/* changes color back to original color after key is released*/
	public void keyReleased () {
		if (_currentKeyColor.equals("Gray")) {
		if (_priorKeyColor.equals("Black")) 
			blackA();
		else whiteA();
		}
	}

	/**
	 * Returns a String representation describing the key.
	 * @return a String representation describing the key.
	 */
	public String toString () {
		return "Key: " + _pitch;
	}
	
	/* Returns a boolean describing if the key is on or off */
	public boolean on () {
		return _isOn;
	}
}
