package utility;

public class InputHandler {

	private static int mouseX,mouseY;
	private static boolean mouseLeftDown,mouseRightDown,mouseOnScreen;
	private static boolean mouseLeftTriggered,mouseRightTriggered;
	private static boolean[] keyPressed = new boolean[256];
	private static boolean[] keyTriggered = new boolean[256];
	
	public static int getMouseX() {
		return mouseX;
	}
	public static void setMouseX(int mouseX) {
		InputHandler.mouseX = mouseX;
	}
	public static int getMouseY() {
		return mouseY;
	}
	public static void setMouseY(int mouseY) {
		InputHandler.mouseY = mouseY;
	}
	
	public static boolean isMouseLeftDown() {
		return mouseLeftDown;
	}
	public static void setMouseLeftDown(boolean mouseLeftDown) {
		InputHandler.mouseLeftDown = mouseLeftDown;
	}
	public static boolean isMouseRightDown() {
		return mouseRightDown;
	}
	public static void setMouseRightDown(boolean mouseRightDown) {
		InputHandler.mouseRightDown = mouseRightDown;
	}
	
	public static boolean isMouseOnScreen() {
		return mouseOnScreen;
	}
	public static void setMouseOnScreen(boolean mouseOnScreen) {
		InputHandler.mouseOnScreen = mouseOnScreen;
	}
	
	public static boolean isMouseLeftClicked() {
		return mouseLeftTriggered;
	}
	public static void setMouseLeftTriggered(boolean v){
		mouseLeftTriggered = v;
	}
	public static boolean isMouseRightClicked() {
		return mouseRightTriggered;
	}
	public static void setMouseRightTriggered(boolean v){
		mouseRightTriggered = v;
	}
	
	public static boolean getKeyPressed(int key) {
		if (0<=key && key<=255)
			return keyPressed[key];
		return false;
	}
	public static void setKeyPressed(int key,boolean pressed) {
		if (0<=key && key<=255)
			keyPressed[key] = pressed;
	}
	
	public static boolean getKeyTriggered(int key) {
		if (0<=key && key<=255)
			return keyTriggered[key];
		return false;
	}
	public static void setKeyTriggered(int key,boolean pressed) {
		if (0<=key && key<=255)
			keyTriggered[key] = pressed;
	}
	
	public static void postprocessInput(){
		keyTriggered = new boolean[256];
		mouseLeftTriggered = false;
		mouseRightTriggered = false;
	}
}
