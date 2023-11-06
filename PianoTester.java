import static org.junit.jupiter.api.Assertions.*;
import javax.swing.*;
import javax.sound.midi.*;
import java.awt.event.*;
import org.junit.jupiter.api.*;

/**
 * Contains a set of unit tests for the Piano class.
 */
class PianoTester {
	private TestReceiver _receiver;
	private Piano _piano;
	private PianoMouseListener _mouseListener;

	private MouseEvent makeMouseEvent (int x, int y) {
		return new MouseEvent(_piano, 0, 0, 0, x, y, 0, false);
	}

	@BeforeEach
	void setup () {
		// A new TestReceiver will be created before running *each*
		// test. Hence, the "turn on" and "turn off" counts will be
		// reset to 0 before *each* test.
		_receiver = new TestReceiver();
		_piano = new Piano(_receiver);
		_mouseListener = _piano.getMouseListener();
	}

	@Test
	void testClickUpperLeftMostPixel () {
		// Pressing the mouse should cause the key to turn on.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
	}

	@Test
	void testDragWithinKey () {
		// Test that pressing and dragging the mouse *within* the same key
		// should cause the key to be turned on only once, not multiple times.
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		_mouseListener.mouseDragged(makeMouseEvent(0, 0));
		assertEquals(_receiver.getKeyOnCount(Piano.START_PITCH), 1);
	}

	@Test
	void testReleasedKey () {
		// Releasing the mouse should cause the key to turn off
		_mouseListener.mousePressed(makeMouseEvent(0, 0));
		_mouseListener.mouseReleased(makeMouseEvent(0, 0));
		assertFalse(_receiver.isKeyOn(Piano.START_PITCH));
	}
	
	@Test
	void testClickBlackKey () {
		// Clicking the black key should only play the black key and not the white key
		_mouseListener.mousePressed(makeMouseEvent(Piano.WHITE_KEY_WIDTH, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH + 1));
		assertFalse(_receiver.isKeyOn(Piano.START_PITCH));
	}
	
	@Test
	void testDragMultKeys () {
		// After dragging through multiple keys, the first key which was pressed should be off
		_mouseListener.mouseDragged(makeMouseEvent(0, 0));
		_mouseListener.mouseDragged(makeMouseEvent(Piano.WHITE_KEY_WIDTH*3, 0));
		assertFalse(_receiver.isKeyOn(Piano.START_PITCH));
	}
	
	@Test
	void testClickUpperRightMostPixel () {
		// The last key is correct
		_mouseListener.mousePressed(makeMouseEvent(Piano.WIDTH-1, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH + 35));
	}
	
	@Test
	void testReverseDragMultKeys () {
		// After dragging through multiple keys, the last key pressed should be on
		_mouseListener.mouseDragged(makeMouseEvent(Piano.WIDTH-1, 0));
		_mouseListener.mouseDragged(makeMouseEvent(0, 0));
		assertTrue(_receiver.isKeyOn(Piano.START_PITCH));
	}
}
