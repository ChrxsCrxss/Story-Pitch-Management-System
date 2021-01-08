package com.cross.data;

import java.util.Set;

import com.cross.beans.Comment;

public interface CommentDAO {

	Comment getById(Integer id);

	Set<Comment> getAll();

	boolean update(Comment t);

	boolean delete(Comment t);

	Comment add(Comment c);

	Set<Comment> getByCommentorId(Integer commentorId);

	Set<Comment> getByRequestId(Integer requestId);

}
