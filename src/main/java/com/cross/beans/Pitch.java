package com.cross.beans;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Pitch {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="author_id")
	private Integer authorId; 
	@Column(name="general_editor_id")
	private Integer generalEditorId; 
	private String title;
	private String tagline;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="status_id")
	private Status status;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="genre_id")
	private Genre genre;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="form_id")
	private Form form; 
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="stage_id")
	private Stage stage; 
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="priority_lvl_id")
	private Priority priorityLevel; 
	private LocalDateTime deadline; 
	private LocalDateTime createdTime;
	private LocalDateTime lastModifiedTime; 
		
	public Pitch() {
		createdTime = lastModifiedTime = LocalDateTime.now();
		deadline = createdTime.plusDays(30);
	}; 
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getAuthorId() {
		return authorId;
	}
	
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	
	public Integer getGeneralEditorId() {
		return generalEditorId;
	}
	
	public void setGeneralEditorId(Integer generalEditorId) {
		this.generalEditorId = generalEditorId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTagline() {
		return tagline;
	}
	
	public void setTagline(String tagLine) {
		this.tagline = tagLine;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Genre getGenre() {
		return genre;
	}
	
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
	
	public Form getForm() {
		return form;
	}
	
	public void setForm(Form form) {
		this.form = form;
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	
	public Priority getPriority() {
		return priorityLevel;
	}
	
	public void setPriority(Priority priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	
	
	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(LocalDateTime lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	@Override
	public String toString() {
		return String.format("deadline: %s\n, createdTime: %s\n, lastModifiedTime: %s\n", 
				deadline.toString(), createdTime.toString(),lastModifiedTime.toString() );
	}
	
	
	/*
	 * 
	 * 
	 * 
	 */
	

}
