package com.cross.services;

import org.springframework.stereotype.Service;

import com.cross.beans.Draft;
import com.cross.data.DraftDAO;
import com.cross.data.DraftHibernate;

@Service
public class DraftServiceImpl implements DraftService {
	
	private DraftDAO draftDAO;
	
	public DraftServiceImpl() {
		draftDAO = new DraftHibernate();
	}
	
	
	@Override
	public Draft add(Draft d) {
		return draftDAO.add(d);
	}

	@Override
	public Draft getDraftByPitchId(Integer id) {
		return draftDAO.getByPitchId(id);
	}

	@Override
	public boolean updateDraft(Draft d) {
		return draftDAO.update(d);
	}

}
