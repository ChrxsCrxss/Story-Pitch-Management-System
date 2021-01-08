package com.cross.beans;

import java.sql.Timestamp;
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
public class Decision {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id; 
	@Column(name="editor_id")
	private Integer editorId; 
	@Column(name="pitch_id")
	private Integer pitchId; 
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="decision_type_id")
	private DecisionType decisionType; 
	private String explanation;
	private LocalDateTime creationTime; 
	
	public Decision() {
		creationTime = LocalDateTime.now(); 
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEditorId() {
		return editorId;
	}
	public void setEditorId(Integer editorId) {
		this.editorId = editorId;
	}
	public Integer getPitchId() {
		return pitchId;
	}
	public void setPitchId(Integer pitchId) {
		this.pitchId = pitchId;
	}
	public DecisionType getDecisionType() {
		return decisionType;
	}
	public void setDecisionType(DecisionType decisionType) {
		this.decisionType = decisionType;
	}
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	} 
	
	

}
