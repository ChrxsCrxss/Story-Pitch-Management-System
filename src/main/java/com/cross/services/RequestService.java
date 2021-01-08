package com.cross.services;

import java.util.Set;

import com.cross.beans.Request;

public interface RequestService {
	
	public Request addRequest(Request r); 
	public Set<Request> getRequestsByParticipantId(Integer personId); 
}
