import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.sound.midi.*;

/**
 * Implements a simulated piano with 36 keys.
 */
public class Piano extends JPanel {
	public static int START_PITCH = 48;
	public static int WHITE_KEY_WIDTH = 40;
	public static int BLACK_KEY_WIDTH = WHITE_KEY_WIDTH/2;
	public static int WHITE_KEY_HEIGHT = 200;
	public static int BLACK_KEY_HEIGHT = WHITE_KEY_HEIGHT/2;
	public static int NUM_WHITE_KEYS_PER_OCTAVE = 7;
	public static int NUM_OCTAVES = 3;
	public static int NUM_WHITE_KEYS = NUM_WHITE_KEYS_PER_OCTAVE * NUM_OCTAVES;
	public static int WIDTH = NUM_WHITE_KEYS * WHITE_KEY_WIDTH;
	public static int HEIGHT = WHITE_KEY_HEIGHT;
	// Different x-coordinates to draw the keyboard
	public static int X_COOR1;
	public static int X_COOR2;
	public static int X_COOR3;
	public static int X_COOR4;
	public static int X_COOR5;
		
	private java.util.List<Key> _keys = new ArrayList<>();
	private Receiver _receiver;
	private PianoMouseListener _mouseListener;

	/**
	 * Returns the list of keys in the piano.
	 * @return the list of keys.
	 */
	public java.util.List<Key> getKeys () {
		return _keys;
	}

	/**
	 * Sets the MIDI receiver of the piano to the specified value.
	 * @param receiver the MIDI receiver 
	 */
	public void setReceiver (Receiver receiver) {
		_receiver = receiver;
	}

	/**
	 * Returns the current MIDI receiver of the piano.
	 * @return the current MIDI receiver 
	 */
	public Receiver getReceiver () {
		return _receiver;
	}

	/**
	 * @param receiver the MIDI receiver to use in the piano.
	 */
	public Piano (Receiver receiver) {
		// Some Swing setup stuff; don't worry too much about it.
		setFocusable(true);
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		setReceiver(receiver);
		_mouseListener = new PianoMouseListener(_keys);
		addMouseListener(_mouseListener);
		addMouseMotionListener(_mouseListener);
		makeKeys();
	}

	/**
	 * Returns the PianoMouseListener associated with the piano.
	 * @return the PianoMouseListener associated with the piano.
	 */
	public PianoMouseListener getMouseListener () {
		return _mouseListener;
	}

	/**
	 * Instantiate all the Key objects with their correct polygons and pitches, and
	 * add them to the _keys array.
	 */
	private void makeKeys () {
		for (int k = 0; k < NUM_OCTAVES; k++) {
			for (int i = 0; i < NUM_WHITE_KEYS_PER_OCTAVE; i++) {
				X_COOR1 = (k*7+i)*WHITE_KEY_WIDTH;
				X_COOR2 = (k*7+i)*WHITE_KEY_WIDTH+WHITE_KEY_WIDTH-BLACK_KEY_WIDTH/2;
				X_COOR3 = (k*7+i)*WHITE_KEY_WIDTH+WHITE_KEY_WIDTH;
				X_COOR4 = (k*7+i)*WHITE_KEY_WIDTH + BLACK_KEY_WIDTH/2;
				X_COOR5 = (k*7+i)*WHITE_KEY_WIDTH + WHITE_KEY_WIDTH + BLACK_KEY_WIDTH/2;
				int[] whiteXCoordsA = new int[] {
					X_COOR1, X_COOR2, X_COOR2, X_COOR3, X_COOR3, X_COOR1
				};
				int[] whiteYCoordsAC = new int[] {
					0, 0, BLACK_KEY_HEIGHT, BLACK_KEY_HEIGHT, WHITE_KEY_HEIGHT, WHITE_KEY_HEIGHT
				};
				int[] whiteXCoordsB = new int[] {
					X_COOR2, X_COOR4, X_COOR4, X_COOR1, X_COOR1, X_COOR3, X_COOR3, X_COOR2
				};
				int[] whiteYCoordsB = new int[] {
					0, 0, BLACK_KEY_HEIGHT, BLACK_KEY_HEIGHT, WHITE_KEY_HEIGHT, WHITE_KEY_HEIGHT, BLACK_KEY_HEIGHT, BLACK_KEY_HEIGHT
				};
				int[] whiteXCoordsC = new int[] {
					X_COOR3, X_COOR4, X_COOR4, X_COOR1, X_COOR1, X_COOR3
				};
				int[] blackXCoords = new int[] {
					X_COOR2, X_COOR5, X_COOR5, X_COOR2
				};
				int[] blackYCoords = new int[] {
					0, 0, BLACK_KEY_HEIGHT, BLACK_KEY_HEIGHT
				};
				if (i == 2 || i == 6) {
					makeWhiteKey(whiteXCoordsC, whiteYCoordsAC);
				}
				else if (i == 0 || i == 3) {
					makeWhiteKey(whiteXCoordsA, whiteYCoordsAC);
					makeBlackKey(blackXCoords, blackYCoords);
				}
				else {
					makeWhiteKey(whiteXCoordsB, whiteYCoordsB);
					makeBlackKey(blackXCoords, blackYCoords);
				}
			}
		}
		START_PITCH = 48;
	}
	
	/* makes a white key*/
	public void makeWhiteKey(int[] x, int[] y) {
		Polygon whitePolygon = new Polygon (x, y, x.length);
		Key key = new Key (whitePolygon, START_PITCH,this);
		START_PITCH ++;
		_keys.add(key);
	}
	
	/* makes a black key*/
	public void makeBlackKey(int[] x, int[] y) {
		Polygon blackPolygon = new Polygon(x, y, x.length);
		Key key = new Key (blackPolygon, START_PITCH,this);
		START_PITCH ++;
		key.blackA();
		_keys.add(key);
	}
	
	@Override
	/**
	 * Paints the piano and all its constituent keys.
	 * @param g the Graphics object to use for painting.
	 */
	public void paint (Graphics g) {
		// Delegates to all the individual keys to draw themselves.
		for (Key key: _keys) {
			key.paint(g);
	}
	}
}
