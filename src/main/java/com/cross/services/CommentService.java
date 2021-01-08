package com.cross.services;

import java.util.Set;

import com.cross.beans.Comment;

public interface CommentService {
	
	public Comment add(Comment c);
	public Set<Comment> getCommentsByRequestId(Integer id); 

}
