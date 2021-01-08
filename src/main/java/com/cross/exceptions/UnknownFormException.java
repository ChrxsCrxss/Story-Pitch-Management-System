package com.cross.exceptions;

public class UnknownFormException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4637711048008001996L;

	public UnknownFormException() {
		super("The literary form is not recognized");
	}
}
