package com.cross.exceptions;

public class UnknownStageException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3408298725065728286L;

	public UnknownStageException() {
		super("The stage is not recognized");
	}
}