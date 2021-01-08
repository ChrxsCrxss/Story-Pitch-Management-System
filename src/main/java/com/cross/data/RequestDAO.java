package com.cross.data;

import java.util.Set;

import com.cross.beans.Request;

public interface RequestDAO {

	Request getById(Integer id);

	Set<Request> getAll();

	boolean update(Request t);

	boolean delete(Request t);

	Request add(Request c);

	Set<Request> getByTargetPitchId(Integer pitchId);

	Set<Request> getByTargetDraftId(Integer draftId);

	Set<Request> getByDecisionId(Integer decisionId);

	Set<Request> getByParticipantId(Integer participantId);

}
