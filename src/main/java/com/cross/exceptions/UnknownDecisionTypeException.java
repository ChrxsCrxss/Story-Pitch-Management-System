package com.cross.exceptions;

public class UnknownDecisionTypeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -182643218235468395L;

	public UnknownDecisionTypeException() {
		super("Unknown decision type");
	}
}
