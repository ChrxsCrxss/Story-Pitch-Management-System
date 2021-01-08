package com.cross.data;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.cross.beans.Decision;
import com.cross.beans.DecisionType;
import com.cross.beans.Pitch;
import com.cross.beans.Stage;
import com.cross.beans.Status;
import com.cross.data.DecisionDAO;
import com.cross.data.UtilityDAO;
import com.cross.exceptions.InvalidGeneralEditorException;
import com.cross.utils.StringGenerator;

/*
 * 
 * Testing the decisionHibernateDAO is tricky because we cannot
 * simply generate and hash a large volume of random data. Since
 * each decision requires a transaction that could effect the
 * status of a pitch and/or draft, we should try to move a 
 * pitch through the pipeline, checking that is has the correct
 * status value after each decision. To facilitate testing, the 
 * database contains a dummy pitch with id = 0; 
 * 
 */

public class DecisionHibernateTest {
	
	private static DecisionDAO decisionDAO = new DecisionHibernate(); 
	private static Pitch dummyPitch = new PitchHibernate().getById(0);
	private static Set<Decision> dSet = new HashSet<Decision>(); 
	
	@DisplayName("testDummyPitch")
	@Test
	@Order(1) 
	public void addTest() {
		assertTrue( dummyPitch != null); 
	}
	
	@DisplayName("approvePitchInGenreStageTest")
	@Test
	@Order(2) 
	public void approvePitchInGenreStageTest() throws Exception {
		
		System.out.println( dummyPitch.getStage().getName() );
		assertTrue(dummyPitch.getStage().getName().equalsIgnoreCase("GENRE REVIEW") );
		LocalDateTime beforeDecision = dummyPitch.getLastModifiedTime(); 
		Decision d = new Decision(); 
		d.setPitchId(0);
		d.setEditorId(2);
		d.setDecisionType(UtilityDAO.getByName(new DecisionType(), "pitch-approval") );
		d.setCreationTime( LocalDateTime.now() );
		d.setExplanation("dfjiapf sadfjpds");
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		Pitch p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("GENERAL REVIEW") );
		assertTrue( p.getLastModifiedTime().isAfter(beforeDecision) );
	}
	
	@DisplayName("approvePitchInGeneralStageTest")
	@Test
	@Order(3) 
	public void approvePitchInGeneralStageTest() throws Exception {
		
		dummyPitch = new PitchHibernate().getById(0);
		assertTrue(dummyPitch.getStage().getName().equalsIgnoreCase("GENERAL REVIEW") );		
		final Decision d = new Decision(); 
		d.setPitchId(0);
		d.setEditorId(3);
		d.setDecisionType(UtilityDAO.getByName(new DecisionType(), "pitch-approval") );
		d.setCreationTime( LocalDateTime.now() );
		d.setExplanation("dfjiapf sadfjpds");
		
		
		// TODO : Figure out exception testing 
//		assertThrows(InvalidGeneralEditorException.class, () -> {
//			
//			try {
//				decisionDAO.add(d);
//			} catch (InvalidGeneralEditorException e ) {
//				e.getMessage();
//			}
//			
//		});
		

		d.setEditorId(3);
		Decision returned = null; 
		try {
			returned = decisionDAO.add(d);
		} catch (InvalidGeneralEditorException e) {
			e.printStackTrace();
		}
		assertTrue(returned != null);
		Pitch p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("SENIOR REVIEW") );
		dummyPitch = p; 
	}
	
	@DisplayName("approvePitchInSeniorStageTest")
	@Test
	@Order(4) 
	public void approvePitchInSeniorStageTest() {
		
		dummyPitch = new PitchHibernate().getById(0);
		System.out.println( dummyPitch.getStage().getName() );
		assertTrue(dummyPitch.getStage().getName().equalsIgnoreCase("SENIOR REVIEW") );		
		Decision d = new Decision(); 
		d.setPitchId(0);
		d.setEditorId(3);
		d.setDecisionType(UtilityDAO.getByName(new DecisionType(), "pitch-approval") );
		d.setCreationTime( LocalDateTime.now() );
		d.setExplanation("dfjiapf sadfjpds");
		try {
			d = decisionDAO.add(d);
		} catch ( Exception e) {
			e.printStackTrace();
		} 
		assertTrue(d != null);
		Pitch p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );
		dummyPitch = p; 
	}
	
	@DisplayName("approvePitchInFinalStageTest")
	@Test
	@Order(5) 
	public void approvePitchInFinalStageTest() throws Exception {
		
		dummyPitch = new PitchHibernate().getById(0);
		System.out.println( dummyPitch.getStage().getName() );
		assertTrue(dummyPitch.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );		
		Decision d = new Decision(); 
		d.setPitchId(0);
		d.setDecisionType(UtilityDAO.getByName(new DecisionType(), "draft-approval") );
		d.setCreationTime( LocalDateTime.now() );
		d.setExplanation("dfjiapf sadfjpds");
		
		
		// Pitch with id 0 has a genre id of 1, and there are 4 members of that committee,
		// so the pitch will need three draft approvals to be accepted; 
		d.setEditorId(2);
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		Pitch p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("PENDING-EDITOR-REVIEW"));
		
		d.setEditorId(4);
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("PENDING-EDITOR-REVIEW"));
		
		d.setEditorId(5);
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("APPROVED"));
		
		p.setStage( UtilityDAO.getByName(new Stage(), "genre review"));
		p.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
		assertTrue ( new PitchHibernate().update(p) );
	}
	
	@DisplayName("approvePitchInGenreStageTest")
	@Test
	@Order(6) 
	public void rejectPitchInGenreStageTest() throws Exception {
		
		System.out.println( dummyPitch.getStage().getName() );
		
		assertTrue(dummyPitch.getStage().getName().equalsIgnoreCase("GENRE REVIEW") );		
		Decision d = new Decision(); 
		d.setPitchId(0);
		d.setEditorId(2);
		d.setDecisionType(UtilityDAO.getByName(new DecisionType(), "pitch-rejection") );
		d.setCreationTime( LocalDateTime.now() );
		d.setExplanation("dfjiapf sadfjpds");
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		Pitch p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("GENRE REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("REJECTED"));

	}
	
	@DisplayName("approvePitchInGenreStageTest")
	@Test
	@Order(7) 
	public void rejectPitchInGeneralStageTest() throws Exception {
		
		System.out.println( dummyPitch.getStage().getName() );
		dummyPitch.setStage( UtilityDAO.getByName(new Stage(), "general review"));
		dummyPitch.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
		new PitchHibernate().update(dummyPitch);
		dummyPitch = new PitchHibernate().getById(0);
		
		assertTrue(dummyPitch.getStage().getName().equalsIgnoreCase("GENERAL REVIEW") );		
		Decision d = new Decision(); 
		d.setPitchId(0);
		d.setEditorId(3);
		d.setDecisionType(UtilityDAO.getByName(new DecisionType(), "pitch-rejection") );
		d.setCreationTime( LocalDateTime.now() );
		d.setExplanation("dfjiapf sadfjpds");
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		Pitch p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("GENERAL REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("REJECTED"));
		dummyPitch = p; 
	}
	
	@DisplayName("approvePitchInSeniorStageTest")
	@Test
	@Order(8) 
	public void rejectPitchInSeniorStageTest() throws Exception {
		
		System.out.println( dummyPitch.getStage().getName() );
		dummyPitch.setStage( UtilityDAO.getByName(new Stage(), "senior review"));
		dummyPitch.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
		new PitchHibernate().update(dummyPitch);
		dummyPitch = new PitchHibernate().getById(0);
		
		assertTrue(dummyPitch.getStage().getName().equalsIgnoreCase("SENIOR REVIEW") );		
		Decision d = new Decision(); 
		d.setPitchId(0);
		d.setEditorId(2);
		d.setDecisionType(UtilityDAO.getByName(new DecisionType(), "pitch-rejection") );
		d.setCreationTime( LocalDateTime.now() );
		d.setExplanation("dfjiapf sadfjpds");
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		Pitch p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("SENIOR REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("REJECTED"));
		dummyPitch = p; 
	}
	
	@DisplayName("rejectPitchInFinalStageTest")
	@Test
	@Order(9) 
	public void rejectPitchInFinalStageTest() throws Exception {
		
		System.out.println( dummyPitch.getStage().getName() );
		dummyPitch.setStage( UtilityDAO.getByName(new Stage(), "final review"));
		dummyPitch.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
		new PitchHibernate().update(dummyPitch);
		dummyPitch = new PitchHibernate().getById(0);
		
		assertTrue(dummyPitch.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );		
		Decision d = new Decision(); 
		d.setPitchId(0);
		d.setDecisionType(UtilityDAO.getByName(new DecisionType(), "draft-rejection") );
		d.setCreationTime( LocalDateTime.now() );
		d.setExplanation("dfjiapf sadfjpds");
		
		
		// Pitch with id 0 has a genre id of 1, and there are 4 members of that committee,
		// so the pitch will need three draft approvals to be accepted; 
		d.setEditorId(2);
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		Pitch p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("PENDING-EDITOR-REVIEW"));
		
		d.setEditorId(4);
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("PENDING-EDITOR-REVIEW"));
		
		d.setEditorId(5);
		d = decisionDAO.add(d); 
		assertTrue(d != null);
		p = new PitchHibernate().getById(0);
		assertTrue( p.getStage().getName().equalsIgnoreCase("FINAL REVIEW") );
		assertTrue( p.getStatus().getName().equalsIgnoreCase("REJECTED"));
		
		p.setStage( UtilityDAO.getByName(new Stage(), "genre review"));
		p.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
		assertTrue ( new PitchHibernate().update(p) );
	}
	
	
	
		
}
