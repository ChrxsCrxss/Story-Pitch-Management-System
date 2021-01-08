package com.cross.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cross.beans.Request;
import com.cross.utils.HibernateUtil;


// TODO: refactor getBy methods to only return open requests
public class RequestHibernate implements RequestDAO {
	
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Request getById(Integer id) {
		Session s = hu.getSession();
		Request c = s.get(Request.class, id);
		s.close();
		return c;
	}

	@Override
	public Set<Request> getAll() {
		Session s = hu.getSession();
		String query = "FROM Request";
		Query<Request> q = s.createQuery(query, Request.class);
		List<Request> requestsList = q.getResultList();
		Set<Request> requestsSet = new HashSet<>();
		requestsSet.addAll(requestsList);
		s.close();
		return requestsSet;
	}
	
	@Override
	public boolean update(Request t) {
		Boolean didUpdate = false; 
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.update(t);
			tx.commit();
			didUpdate = true; 
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				didUpdate = false; 
			}
		} finally {
			s.close();
		}
		return didUpdate; 
	}
	
	@Override
	public boolean delete(Request t) {
		Boolean didDelete = false; 
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.delete(t);
			tx.commit();
			didDelete = true; 
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				didDelete = false; 
			}
		} finally {
			s.close();
		}
		return didDelete; 
	}
	
	@Override
	public Request add(Request c) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.save(c);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				// Give indication of failure with null return value
				c = null; 
				throw e; 
			}
		} finally {
			s.close();
		}
		return c;
	}
	
	@Override
	public Set<Request> getByTargetPitchId(Integer pitchId) {

		Session s = hu.getSession();
		String query = "FROM Request where target_pitch_id = :id";
		Query<Request> q = s.createQuery(query, Request.class);
		q.setParameter("id", pitchId);
		List<Request> requestsList = q.getResultList();
		Set<Request> requestsSet = new HashSet<>();
		requestsSet.addAll(requestsList);
		s.close();
		return requestsSet;
	}
	
	@Override
	public Set<Request> getByTargetDraftId(Integer draftId) {

		Session s = hu.getSession();
		String query = "FROM Request where target_draft_id = :id";
		Query<Request> q = s.createQuery(query, Request.class);
		q.setParameter("id", draftId);
		List<Request> requestsList = q.getResultList();
		Set<Request> requestsSet = new HashSet<>();
		requestsSet.addAll(requestsList);
		s.close();
		return requestsSet;
	}
	
	@Override
	public Set<Request> getByDecisionId(Integer decisionId) {

		Session s = hu.getSession();
		String query = "FROM Request where target_decision_id = :id";
		Query<Request> q = s.createQuery(query, Request.class);
		q.setParameter("id", decisionId);
		List<Request> requestsList = q.getResultList();
		Set<Request> requestsSet = new HashSet<>();
		requestsSet.addAll(requestsList);
		s.close();
		return requestsSet;
	}
	
	@Override
	public Set<Request> getByParticipantId(Integer participantId) {

		Session s = hu.getSession();
		String query = "FROM Request where sender_id = :id or reciever_id = :id";
		Query<Request> q = s.createQuery(query, Request.class);
		q.setParameter("id", participantId);
		List<Request> requestsList = q.getResultList();
		Set<Request> requestsSet = new HashSet<>();
		requestsSet.addAll(requestsList);
		s.close();
		return requestsSet;
	}
}
