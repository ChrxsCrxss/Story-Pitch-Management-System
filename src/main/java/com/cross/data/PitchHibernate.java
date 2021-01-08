package com.cross.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cross.beans.Person;
import com.cross.beans.Pitch;
import com.cross.utils.HibernateUtil;

public class PitchHibernate implements PitchDAO {
	
private HibernateUtil hu = HibernateUtil.getHibernateUtil();
private PersonDAO personHib = new PersonHibernate(); 
	@Override
	public Pitch getById(Integer id) {
		Session s = hu.getSession();
		Pitch c = s.get(Pitch.class, id);
		s.close();
		return c;
	}

	@Override
	public Set<Pitch> getAll() {
		Session s = hu.getSession();
		String query = "FROM Pitch";
		Query<Pitch> q = s.createQuery(query, Pitch.class);
		List<Pitch> pitchsList = q.getResultList();
		Set<Pitch> pitchsSet = new HashSet<>();
		pitchsSet.addAll(pitchsList);
		s.close();
		return pitchsSet;
	}
	
	@Override
	public boolean update(Pitch t) {
		System.out.println("Attempting update");
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			t.setLastModifiedTime( LocalDateTime.now() );
			s.update(t);
			tx.commit();
			System.out.println("Update successful");
			return true; 
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace(); 
			return false;
		} finally {
			s.close();
		}
	}
	
	@Override
	public boolean delete(Pitch t) {
		Boolean didDelete = false; 
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			Person author = personHib.getById( t.getAuthorId() ); 
			author.setPoints( author.getPoints() + t.getForm().getPoints() );
			s.update(author);
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
	public Pitch add(Pitch c) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			
			if ( ! c.getStatus().getName().equalsIgnoreCase("ON HOLD") ) {
				Person author = personHib.getById( c.getAuthorId() ); 
				author.setPoints( author.getPoints() - c.getForm().getPoints() );
				s.update(author);
			}
			s.save(c);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				e.printStackTrace();
				// Give indication of failure with null return value
				c = null; 
			}
		} finally {
			s.close();
		}
		return c;
	}
	
	@Override
	public Set<Pitch> getByAuthorId(Integer pitchId) {

		Session s = hu.getSession();
		String query = "FROM Pitch where author_id = :id";
		Query<Pitch> q = s.createQuery(query, Pitch.class);
		q.setParameter("id", pitchId);
		List<Pitch> pitchsList = q.getResultList();
		Set<Pitch> pitchsSet = new HashSet<>();
		pitchsSet.addAll(pitchsList);
		s.close();
		return pitchsSet;
	}

	@Override
	public Set<Pitch> getByGenre(String genreName) {

		Session s = hu.getSession();
		String query = "FROM Pitch where genre.name = :id";
		Query<Pitch> q = s.createQuery(query, Pitch.class);
		q.setParameter("id", genreName);
		List<Pitch> pitchsList = q.getResultList();
		Set<Pitch> pitchsSet = new HashSet<>();
		pitchsSet.addAll(pitchsList);
		s.close();
		return pitchsSet;
	}
	
	@Override
	public Set<Pitch> getByStatus(String statusName) {

		Session s = hu.getSession();
		String query = "FROM Pitch where status.name = :id";
		Query<Pitch> q = s.createQuery(query, Pitch.class);
		q.setParameter("id", statusName);
		List<Pitch> pitchsList = q.getResultList();
		Set<Pitch> pitchsSet = new HashSet<>();
		pitchsSet.addAll(pitchsList);
		s.close();
		return pitchsSet;
	}
	
	@Override
	public Set<Pitch> getByStage(String stageName) {

		Session s = hu.getSession();
		String query = "FROM Pitch where stage.name = :id";
		Query<Pitch> q = s.createQuery(query, Pitch.class);
		q.setParameter("id", stageName);
		List<Pitch> pitchsList = q.getResultList();
		Set<Pitch> pitchsSet = new HashSet<>();
		pitchsSet.addAll(pitchsList);
		s.close();
		return pitchsSet;
	}
	
	@Override
	public Set<Pitch> getByGeneralEditorId(Integer generalEditorId) {
		System.out.println("here");
		Session s = hu.getSession();
		String query = "FROM Pitch where general_editor_id = :ge_id";
		Query<Pitch> q = s.createQuery(query, Pitch.class);
		q.setParameter("ge_id", generalEditorId);
		List<Pitch> pitchsList = q.getResultList();
		Set<Pitch> pitchsSet = new HashSet<>();
		pitchsSet.addAll(pitchsList);
		s.close();
		return pitchsSet;
	}
		
	
}
