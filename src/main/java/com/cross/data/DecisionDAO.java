package com.cross.data;

import java.util.Set;

import com.cross.beans.Decision;
import com.cross.exceptions.InvalidGeneralEditorException;

public interface DecisionDAO {

	Decision getById(Integer id);

	Set<Decision> getAll();

	boolean update(Decision t);

	boolean delete(Decision t);

	Decision add(Decision c) throws Exception;

	Set<Decision> getByEditorId(Integer editorId);

	Set<Decision> getByPitchId(Integer pitchId);
	

}
