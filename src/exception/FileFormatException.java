package exception;

import javax.swing.JOptionPane;

public class FileFormatException extends Exception {

	public FileFormatException() {
		JOptionPane.showMessageDialog(null, "Error Format is incorrect");
	}
	
}
