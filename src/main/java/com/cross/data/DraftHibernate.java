package com.cross.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cross.beans.Draft;
import com.cross.beans.Pitch;
import com.cross.utils.HibernateUtil;

public class DraftHibernate implements DraftDAO {
	
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
private PitchHibernate pitchHib = new PitchHibernate(); 

	@Override
	public Draft getById(Integer id) {
		Session s = hu.getSession();
		Draft c = s.get(Draft.class, id);
		s.close();
		return c;
	}

	@Override
	public Set<Draft> getAll() {
		Session s = hu.getSession();
		String query = "FROM Draft";
		Query<Draft> q = s.createQuery(query, Draft.class);
		List<Draft> draftsList = q.getResultList();
		Set<Draft> draftsSet = new HashSet<>();
		draftsSet.addAll(draftsList);
		s.close();
		return draftsSet;
	}
	
	@Override
	public boolean update(Draft d) {
		Boolean didUpdate = false; 
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			Pitch p = pitchHib.getById( d.getPitchId() );
			p.setLastModifiedTime( LocalDateTime.now() );
			s.update(p);
			s.update(d);
			tx.commit();
			return true; 
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				e.printStackTrace();
				return false; 
			}
		} finally {
			s.close();
		}
		return didUpdate; 
	}
	
	@Override
	public boolean delete(Draft t) {
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
	public Draft add(Draft c) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			Pitch p = pitchHib.getById( c.getPitchId() );
			p.setLastModifiedTime( LocalDateTime.now() );
			s.update(p);
			s.save(c);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				e.printStackTrace();
				c = null; 
			}
		} finally {
			s.close();
		}
		return c;
	}
	
	/**
	 * Pitches and drafts should be in one-to-one
	 * correspondence
	 */
	@Override
	public Draft getByPitchId(Integer pitchId) {

		// Criteria API: a way of making queries in a programmatic syntax
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Draft> criteria = cb.createQuery(Draft.class);
		Root<Draft> root = criteria.from(Draft.class);
		
		Predicate predicateForUsername = cb.equal(root.get("pitchId"), pitchId);
		// Predicate predicateForPassword = cb.equal(root.get("passwd"), password);
		// Predicate predicateForBoth = cb.and(predicateForUsername, predicateForPassword);
		
		criteria.select(root).where(predicateForUsername);
		
		Draft d = s.createQuery(criteria).getSingleResult();
		return d;

	}
	
	@Override
	public Set<Draft> getByAuthorId(Integer pitchId) {

		Session s = hu.getSession();
		String query = "FROM Draft where pitch_id = :id";
		Query<Draft> q = s.createQuery(query, Draft.class);
		q.setParameter("id", pitchId);
		List<Draft> draftsList = q.getResultList();
		Set<Draft> draftsSet = new HashSet<>();
		draftsSet.addAll(draftsList);
		s.close();
		return draftsSet;
	}

}
