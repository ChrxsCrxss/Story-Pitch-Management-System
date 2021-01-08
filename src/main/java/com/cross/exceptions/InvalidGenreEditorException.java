package com.cross.exceptions;

public class InvalidGenreEditorException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1223858765265418560L;

	public InvalidGenreEditorException() {
		super("Editor who is not general editor not in same genre as pitch.");
	}
}
