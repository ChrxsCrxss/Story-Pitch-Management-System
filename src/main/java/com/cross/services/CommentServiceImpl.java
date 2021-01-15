package com.cross.services;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.cross.beans.Comment;
import com.cross.data.CommentDAO;
import com.cross.data.CommentHibernate;

@Service 
public class CommentServiceImpl implements CommentService {
	
	private CommentDAO commentDAO; 
	
	public CommentServiceImpl() {
		commentDAO = new CommentHibernate(); 
	}

	@Override
	public Comment add(Comment c) {
		c.setCreationTime( LocalDateTime.now() );
		return commentDAO.add(c);
	}

	@Override
	public Set<Comment> getCommentsByRequestId(Integer id) {
		return commentDAO.getByRequestId(id);
	}

}
