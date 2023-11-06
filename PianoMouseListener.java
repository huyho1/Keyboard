import javax.swing.*;
import java.awt.event.*;
import javax.sound.midi.*;
import java.util.*;

/**
 * Handles mouse press, release, and drag events on the Piano.
 */
public class PianoMouseListener extends MouseAdapter {
	private List<Key> _keys;
	private List<Key> PressedOnKeys;

	/**
	 * @param keys the list of keys in the piano.
	 */
	public PianoMouseListener (List<Key> keys) {
		_keys = keys;
	}

	@Override
	/**
	 * This method is called by Swing whenever the user drags the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseDragged (MouseEvent e) {
		for (Key key : _keys) {
	        if (key.getPolygon().contains(e.getX(), e.getY())) {
	            if (!(key.on())) {
	            	key.keyPressed();
	            	key.play(true);
	            }
	        } else {
	        	key.play(false);
	            key.keyReleased();
	        }
		}
	}
		
	
	@Override
	/**
	 * This method is called by Swing whenever the user presses the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mousePressed (MouseEvent e) {
		for (Key key : _keys) {
		if (key.getPolygon().contains(e.getX(), e.getY())) {
			key.play(true);
			key.keyPressed();
		}
		}
	}

	@Override
	/**
	 * This method is called by Swing whenever the user releases the mouse.
	 * @param e the MouseEvent containing the (x,y) location, relative to the upper-left-hand corner
	 * of the entire piano, of where the mouse is currently located.
	 */
	public void mouseReleased (MouseEvent e) {
		for (Key key : _keys) {
				key.play(false);
				key.keyReleased();
			}
		}
		
}
