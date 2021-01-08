package com.cross.data;

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

import com.cross.beans.Draft;
import com.cross.beans.Status;
import com.cross.data.DraftDAO;
import com.cross.data.DraftHibernate;
import com.cross.data.UtilityDAO;
import com.cross.utils.StringGenerator;

public class DraftHibernateTest {
	
	private static DraftDAO draftDAO = new DraftHibernate(); 
	private static Set<Draft> testDrafts = new HashSet<Draft>(); 
	
	/*
	 * Since we are using JavaBeans, we have to manually set 
	 * at least once. 
	 */
	private static void generateTestPitches() {
		Random rand = new Random(); 
		for (int i = 0; i < 5; ++i) {
			Draft dr = new Draft(); 
			dr.setAuthorId(1);
			// Dummy pitch IDs are negative 
			dr.setPitchId( (-1 * rand.nextInt(3)) - 1 );
			dr.setContent( StringGenerator.randomString(10) );
			Status s = new Status(); 
			s.setId( 1 + rand.nextInt(4) );
			s.setName( UtilityDAO.getById(s, s.getId()).getName() );
			dr.setStatus(s);
			dr.setCreatedTime( LocalDateTime.now() );
			dr.setLastModifiedTime( LocalDateTime.now() );
		}
	}
	
	
	@DisplayName("addTest")
	@Test
	@Order(1) 
	public void addTest() {
		generateTestPitches(); 
		testDrafts.forEach( draft -> {
			Draft dr = null; 
			dr = draftDAO.add(draft);
			assertTrue(dr != null);
			draft.setId( dr.getId());
		});
	}
	
	@DisplayName("getByIdTest")
	@Test
	@Order(2) 
	public void getByIdTest() {
		testDrafts.forEach( draft -> {
			Draft dr = null; 
			dr = draftDAO.getById( draft.getId() );
			assertTrue(dr != null && dr.getId() == draft.getId() );
		});
		
		assertTrue( null == draftDAO.getById(-1) );
	}
	
	@DisplayName("getByAuthorIdTest")
	@Test
	@Order(3)
	public void getByAuthorIdTest() {
		
		Map<Integer, Integer> idMap = new HashMap<Integer, Integer>(); 
		Integer authorIds[] = {1};
		for (Integer id : authorIds) { idMap.put(id, 0); }
		testDrafts.forEach(dr -> {
			Integer authorId = dr.getAuthorId(); 
			idMap.put(authorId, idMap.get(authorId) + 1);
		});
		
		for (Integer id : authorIds) {
			Set<Draft> byAuthorId = draftDAO.getByAuthorId(id);
			System.out.println("Query size: " + idMap.get(id));
			System.out.println("Map size: " + byAuthorId.size());
			assertTrue( byAuthorId.size() == idMap.get(id) );
		}
	}
	
	@DisplayName("getByPitchIdTest")
	@Test
	@Order(4)
	public void getByPitchIdTest() {
		testDrafts.forEach(dr -> {
			assertTrue( draftDAO.getByPitchId(dr.getId()).equals(dr) );
		});
	}
	
	@DisplayName("deleteTest")
	@Test
	@Order(5)
	public void deleteTest() {
		testDrafts.forEach( draft -> {
			assertTrue( draftDAO.delete(draft) );
		});
	}

}



/*

	id serial primary key, 
	author_id integer references person,
	pitch_id integer references pitch, 
	status_id integer references status,
	draft_content varchar(1000000) not NULL,
	createdTime TIMESTAMP NOT null, 
	lastModifiedTime TIMESTAMP NOT null

*/