package com.cross.data;

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

import com.cross.beans.Person;
import com.cross.utils.HibernateUtil;

public class PersonHibernate implements PersonDAO {
	
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Person getById(Integer id) {
		Session s = hu.getSession();
		Person c = s.get(Person.class, id);
		s.close();
		return c;
	}

	@Override
	public Set<Person> getAll() {
		Session s = hu.getSession();
		String query = "FROM Person";
		Query<Person> q = s.createQuery(query, Person.class);
		List<Person> personsList = q.getResultList();
		Set<Person> personsSet = new HashSet<>();
		personsSet.addAll(personsList);
		s.close();
		return personsSet;
	}
	
	@Override
	public boolean update(Person t) {
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
	public boolean delete(Person t) {
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
	public Person add(Person c) {
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
	public Person getByUsername(String username) {
		// Criteria API: a way of making queries in a programmatic syntax
		Session s = hu.getSession();
		CriteriaBuilder cb = s.getCriteriaBuilder();
		CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
		Root<Person> root = criteria.from(Person.class);
		
		Predicate predicateForUsername = cb.equal(root.get("username"), username);
		
		criteria.select(root).where(predicateForUsername);
		
		Person p = s.createQuery(criteria).getSingleResult();
		return p;
	}

}
