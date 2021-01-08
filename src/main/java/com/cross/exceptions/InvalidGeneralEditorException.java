package com.cross.exceptions;

public class InvalidGeneralEditorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1223858765265418560L;

	public InvalidGeneralEditorException() {
		super("General editor cannot be in same genre as pitch");
	}
}
