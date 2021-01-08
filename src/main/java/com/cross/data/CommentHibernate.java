package com.cross.data;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cross.beans.Comment;
import com.cross.utils.HibernateUtil;


public class CommentHibernate implements CommentDAO {
	
	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	@Override
	public Comment getById(Integer id) {
		Session s = hu.getSession();
		Comment c = s.get(Comment.class, id);
		s.close();
		return c;
	}

	@Override
	public Set<Comment> getAll() {
		Session s = hu.getSession();
		String query = "FROM Comment";
		Query<Comment> q = s.createQuery(query, Comment.class);
		List<Comment> commentsList = q.getResultList();
		Set<Comment> CommentsSet = new HashSet<>();
		CommentsSet.addAll(commentsList);
		s.close();
		return CommentsSet;
	}
	
	@Override
	public boolean update(Comment t) {
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
	public boolean delete(Comment t) {
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
	public Comment add(Comment c) {
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
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
	public Set<Comment> getByRequestId(Integer requestId) {
		
		Session s = hu.getSession();
		String query = "FROM Comment where request_id = :id";
		Query<Comment> q = s.createQuery(query, Comment.class);
		q.setParameter("id", requestId);
		List<Comment> commentsList = q.getResultList();
		Set<Comment> commentsSet = new HashSet<>();
		commentsSet.addAll(commentsList);
		s.close();
		return commentsSet;

	}

	@Override
	public Set<Comment> getByCommentorId(Integer commentorId) {

		Session s = hu.getSession();
		String query = "FROM Comment where commenter_id = :id";
		Query<Comment> q = s.createQuery(query, Comment.class);
		q.setParameter("id", commentorId);
		List<Comment> commentsList = q.getResultList();
		Set<Comment> commentsSet = new HashSet<>();
		commentsSet.addAll(commentsList);
		s.close();
		return commentsSet;
	}


}
