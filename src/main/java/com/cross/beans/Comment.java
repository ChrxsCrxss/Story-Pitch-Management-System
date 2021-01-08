package com.cross.beans;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Comment {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	@Column(name="request_id")
	private Integer requestId;
	@Column(name="commentor_id")
	private Integer commentorId;
	@Column(name="comment_content")
	private String content; 
	private LocalDateTime creationTime; 
	
	public Comment() {
		creationTime = LocalDateTime.now(); 
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRequestId() {
		return requestId;
	}

	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}

	public Integer getCommentorId() {
		return commentorId;
	}

	public void setCommentorId(Integer commenterId) {
		this.commentorId = commenterId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
	
	
}