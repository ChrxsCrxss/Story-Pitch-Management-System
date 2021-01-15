package com.cross.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.cross.beans.Decision;
import com.cross.data.DecisionDAO;
import com.cross.data.DecisionHibernate;
import com.cross.exceptions.InvalidGeneralEditorException;

@Service 
public class DecisionServiceImpl implements DecisionService {
	
	private DecisionDAO decisionDAO; 
	
	public DecisionServiceImpl() {
		decisionDAO = new DecisionHibernate(); 
	}
	@Override
	public Decision add(Decision d) throws Exception {
		try {
			return decisionDAO.add(d);
		} catch (Exception e) {
			e.printStackTrace();
			throw e; 
		}
	}

	@Override
	public Set<Decision> getDecisionsByPitchId(Integer id) {
		return decisionDAO.getByPitchId(id);
	}

}
