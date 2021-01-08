package com.cross.services;

import java.util.Set;

import com.cross.beans.Decision;
import com.cross.exceptions.InvalidGeneralEditorException;

public interface DecisionService {
	
	public Decision add(Decision d) throws Exception; 
	public Set<Decision> getDecisionsByPitchId(Integer id);
}
