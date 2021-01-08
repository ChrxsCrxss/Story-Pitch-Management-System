package com.cross.data;

import java.util.Set;

import com.cross.beans.Pitch;

public interface PitchDAO {

	Set<Pitch> getByGeneralEditorId(Integer generalEditorId);

	Set<Pitch> getByStage(String stageName);

	Set<Pitch> getByStatus(String statusName);

	Set<Pitch> getByGenre(String genreName);

	Set<Pitch> getByAuthorId(Integer pitchId);

	Pitch add(Pitch c);

	boolean delete(Pitch t);

	boolean update(Pitch t);

	Set<Pitch> getAll();

	Pitch getById(Integer id);

}
