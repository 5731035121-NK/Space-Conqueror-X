package exception;

import javax.swing.JOptionPane;

public class FileMissingException extends Exception {

	public FileMissingException() {
		// TODO Auto-generated constructor stub
	}

	public FileMissingException(String message) {
		super(message);

		JOptionPane.showMessageDialog(null, "Error File Missing : " + message);
	}

	public FileMissingException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public FileMissingException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FileMissingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

}
