package utility;

import java.awt.event.KeyEvent;

public class InputUtility {

	private static int mouseX, mouseY;
	private static boolean mouseLeftDown, mouseRightDown, mouseOnScreen;
	private static boolean mouseLeftTriggered, mouseRightTriggered;
	private static boolean[] keyPressed = new boolean[256];
	private static boolean[] keyTriggered = new boolean[256];
	private static int[] keyBinding;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int NORMAL = 2;
	public static final int PIECE = 3;
	public static final int ICE = 4;
	public static final int PAUSE = 5;

	public static int getMouseX() {
		return mouseX;
	}

	public static void setMouseX(int mouseX) {
		InputUtility.mouseX = mouseX;
	}

	public static int getMouseY() {
		return mouseY - 22;
	}

	public static void setMouseY(int mouseY) {
		InputUtility.mouseY = mouseY;
	}

	public static boolean isMouseLeftDown() {
		return mouseLeftDown;
	}

	public static void setMouseLeftDown(boolean mouseLeftDown) {
		InputUtility.mouseLeftDown = mouseLeftDown;
	}

	public static boolean isMouseRightDown() {
		return mouseRightDown;
	}

	public static void setMouseRightDown(boolean mouseRightDown) {
		InputUtility.mouseRightDown = mouseRightDown;
	}

	public static boolean isMouseOnScreen() {
		return mouseOnScreen;
	}

	public static void setMouseOnScreen(boolean mouseOnScreen) {
		InputUtility.mouseOnScreen = mouseOnScreen;
	}

	public static boolean isMouseLeftClicked() {
		return mouseLeftTriggered;
	}

	public static void setMouseLeftTriggered(boolean v) {
		mouseLeftTriggered = v;
	}

	public static boolean isMouseRightClicked() {
		return mouseRightTriggered;
	}

	public static void setMouseRightTriggered(boolean v) {
		mouseRightTriggered = v;
	}

	public static boolean getKeyPressed(int key) {
		if (0 <= key && key <= 255)
			return keyPressed[key];
		return false;
	}

	public static void setKeyPressed(int key, boolean pressed) {
		if (0 <= key && key <= 255)
			keyPressed[key] = pressed;
	}

	public static boolean getKeyTriggered(int key) {
		if (0 <= key && key <= 255)
			return keyTriggered[key];
		return false;
	}

	public static void setKeyTriggered(int key, boolean pressed) {
		if (0 <= key && key <= 255)
			keyTriggered[key] = pressed;
	}
	
	public static void setKeyBind(int index, int key) {
		if (0 <= index && index <6) {
			keyBinding[index] = key;
			GameLoader.saveKeyBind();
		}
	}
	
	public static int getKeyBind(int index) {
		if (0 <= index && index <6) {
			return keyBinding[index];
		}
		return 0;
	}

	public static boolean isLeft() {
		return keyPressed[keyBinding[0]];
	}

	public static boolean isRight() {
		return keyPressed[keyBinding[1]];
	}

	public static boolean isFireNormal() {
		return keyPressed[keyBinding[2]];
	}

	public static boolean isFirePiece() {
		return keyPressed[keyBinding[3]];
	}

	public static boolean isFireIce() {
		return keyPressed[keyBinding[4]];
	}

	public static boolean isPauseButton() {
		return keyTriggered[keyBinding[5]];
	}

	public static void postUpdate() {
		keyTriggered = new boolean[256];
		mouseLeftTriggered = false;
		mouseRightTriggered = false;
	}

	public static void initKeyBind() {
		keyBinding = new int[6];
	}
}
