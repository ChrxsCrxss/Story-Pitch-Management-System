package com.cross.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.cross.beans.Request;
import com.cross.data.RequestDAO;
import com.cross.data.RequestHibernate;

@Service
public class RequestServiceImpl implements RequestService {

	private RequestDAO requestDAO;
	
	public RequestServiceImpl() {
		requestDAO = new RequestHibernate(); 
	}

	@Override
	public Set<Request> getRequestsByParticipantId(Integer id) {
		return requestDAO.getByParticipantId(id);
	}

	@Override
	public Request addRequest(Request r) {
		return requestDAO.add(r);
	}
}
