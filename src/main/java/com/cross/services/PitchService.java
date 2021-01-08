package com.cross.services;

import java.util.Set;

import com.cross.beans.Pitch;

public interface PitchService {
	
	Set<Pitch> getPitchesByGenre(String genre);
	Set<Pitch> getPitchesByGeneralEditorId(Integer id);
	Set<Pitch> getPitchesByAuthorId(Integer id); 
	public Pitch addPitch(Pitch p);
	public Set<Pitch> getAll(); 
	public Pitch getPitchById(Integer id);
	public boolean updatePitch(Pitch p);
	public boolean deletePitch(Pitch p);

}
