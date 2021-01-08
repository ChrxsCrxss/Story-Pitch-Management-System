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
public class Request {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	@Column(name="sender_id")
	private Integer senderId; 
	@Column(name="reciever_id")
	private Integer recieverId; 
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="status_id")
	private Status status;
	private LocalDateTime creationTime; 
	@Column(name="target_draft_id")
	private Integer targetDraftId;
	@Column(name="target_pitch_id")
	private Integer targetPitchId;
	@Column(name="target_decision_id")
	private Integer targetDecisionId; 
	
	public Request() {
		
		creationTime = LocalDateTime.now();
		status = new Status(); 
		status.setName("open");
		status.setId(5);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	

	public Integer getSenderId() {
		return senderId;
	}

	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}

	public Integer getRecieverId() {
		return recieverId;
	}

	public void setRecieverId(Integer recieverId) {
		this.recieverId = recieverId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}

	public Integer getTargetDraftId() {
		return targetDraftId;
	}

	public void setTargetDraftId(Integer targetDraftId) {
		this.targetDraftId = targetDraftId;
	}

	public Integer getTargetPitchId() {
		return targetPitchId;
	}

	public void setTargetPitchId(Integer targetPitchId) {
		this.targetPitchId = targetPitchId;
	}

	public Integer getTargetDecisionId() {
		return targetDecisionId;
	}

	public void setTargetDecisionId(Integer targetDecisionId) {
		this.targetDecisionId = targetDecisionId;
	}
	
	
	
	
}
