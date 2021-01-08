package com.cross.data;

import java.util.Set;

import com.cross.beans.Draft;

public interface DraftDAO {

	Draft getById(Integer id);

	Set<Draft> getAll();

	boolean update(Draft t);

	boolean delete(Draft t);

	Draft add(Draft c);

	Draft getByPitchId(Integer pitchId);

	Set<Draft> getByAuthorId(Integer pitchId);

}
