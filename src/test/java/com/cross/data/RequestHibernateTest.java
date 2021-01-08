package com.cross.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.cross.beans.Form;
import com.cross.beans.Genre;
import com.cross.beans.Pitch;
import com.cross.beans.Priority;
import com.cross.beans.Request;
import com.cross.beans.Stage;
import com.cross.beans.Status;
import com.cross.data.RequestDAO;
import com.cross.data.RequestHibernate;
import com.cross.data.UtilityDAO;

public class RequestHibernateTest {
	
	private static RequestDAO requestDAO = new RequestHibernate(); 
	private static Set<Request> testRequests = new HashSet<Request>(); 
	
	/*
	 * Since we are using JavaBeans, we have to manually set 
	 * at least once. 
	 */
	private static void generateTestRequests() {
		Random rand = new Random(); 
		for (int i = 0; i < 20; ++i) {
			Request rq = new Request(); 
			rq.setSenderId( 1 + rand.nextInt(3) );
			do {
				rq.setRecieverId( 1 + rand.nextInt(3) );
			} while (rq.getSenderId() == rq.getRecieverId() );
			Status s = new Status(); 
			s.setId(5 + rand.nextInt(1) );
			s.setName( UtilityDAO.getById(s, s.getId()).getName());
			rq.setStatus(s);
			rq.setCreationTime( LocalDateTime.now() );
			rq.setTargetPitchId(-1);
			rq.setTargetDraftId(-1);
			rq.setTargetDecisionId(-1);
			testRequests.add(rq);
		}
	}
	
	
	@DisplayName("addTest")
	@Test
	@Order(1) 
	public void addTest() {
		generateTestRequests(); 
		testRequests.forEach( request -> {
			Request q = null; 
			q = requestDAO.add(request);
			assertTrue(q != null);
			request.setId( q.getId());
		});
	}
	
	@DisplayName("getByIdTest")
	@Test
	@Order(2) 
	public void getByIdTest() {
		testRequests.forEach( request -> {
			Request q = null; 
			q = requestDAO.getById( request.getId() );
			assertTrue(q != null && q.getId() == request.getId() );
		});
		
		assertTrue( null == requestDAO.getById(-1) );
	}

}


/*

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
			}
		} finally {
			s.close();
		}
		return c;
	}
	
	@Override
	public Set<Request> getByTargetPitchId(Integer requestId) {

		Session s = hu.getSession();
		String query = "FROM Request where target_request_id = :id";
		Query<Request> q = s.createQuery(query, Request.class);
		q.setParameter("id", requestId);
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

*/