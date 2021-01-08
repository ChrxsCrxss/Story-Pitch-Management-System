package com.cross.exceptions;

public class NonExclusiveAuthorRole extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6315952773781440825L;

	public NonExclusiveAuthorRole() {
		super("Persons with author role cannot take on another role");
	}

}
