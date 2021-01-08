package com.cross.services;

import com.cross.beans.Draft;

public interface DraftService {
	
	public Draft add(Draft d); 
	public Draft getDraftByPitchId(Integer id);
	public boolean updateDraft(Draft d);
}
