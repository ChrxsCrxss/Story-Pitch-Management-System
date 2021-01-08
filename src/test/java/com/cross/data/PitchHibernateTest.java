package com.cross.data;

import static org.junit.jupiter.api.Assertions.assertFalse;
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

import com.cross.beans.Form;
import com.cross.beans.Genre;
import com.cross.beans.Pitch;
import com.cross.beans.Priority;
import com.cross.beans.Stage;
import com.cross.beans.Status;
import com.cross.data.PitchDAO;
import com.cross.data.PitchHibernate;
import com.cross.data.UtilityDAO;
import com.cross.utils.StringGenerator;

public class PitchHibernateTest {
	
	private static PitchDAO pitchDAO = new PitchHibernate(); 
	private static Set<Pitch> testPitches = new HashSet<Pitch>(); 
	
	
	/*
	 * Since we are using JavaBeans, we have to manually set 
	 * at least once. 
	 */
	private static void generateTestPitches() {
		Random rand = new Random(); 
		for (int i = 0; i < 10; ++i) {
			Pitch p = new Pitch(); 
			p.setAuthorId(1);
			p.setGeneralEditorId(2);
			p.setTitle( StringGenerator.randomString(5) );
			p.setTagline( StringGenerator.randomString(5) );
			Form f = new Form(); 
			f.setId( 1 + rand.nextInt(4) );
			f.setName( UtilityDAO.getById(f, f.getId()).getName() );
			p.setForm(f);
			Genre g = new Genre(); 
			g.setId( 1 + rand.nextInt(10) );
			g.setName( UtilityDAO.getById(g, g.getId()).getName() );
			p.setGenre(g);
			Priority pr = new Priority(); 
			pr.setId( 1 + rand.nextInt(3) );
			p.setPriority(pr);
			Status s = new Status(); 
			s.setId( 1 + rand.nextInt(4) );
			s.setName( UtilityDAO.getById(s, s.getId()).getName() );
			p.setStatus(s);
			Stage sg = new Stage(); 
			sg.setId( 1 + rand.nextInt(4) );
			p.setStage(sg);
			LocalDateTime now = LocalDateTime.now(); 
			p.setCreatedTime(now);
			p.setLastModifiedTime(now);
			p.setDeadline( now.plusDays(30));
			testPitches.add(p);
		}
	}
	
	@DisplayName("addTest")
	@Test
	@Order(1) 
	public void addTest() {
		generateTestPitches(); 
		testPitches.forEach( pitch -> {
			Pitch q = null; 
			q = pitchDAO.add(pitch);
			System.out.println("id: " + q.getGenre().getId());
			assertTrue(q != null);
			pitch.setId( q.getId());
		});
	}
	
	@DisplayName("getByIdTest")
	@Test
	@Order(2) 
	public void getByIdTest() {
		testPitches.forEach( pitch -> {
			Pitch q = null; 
			q = pitchDAO.getById( pitch.getId() );
			assertTrue(q != null && q.getId() == pitch.getId() );
		});
		
		assertTrue( null == pitchDAO.getById(-1) );
	}
	
	@DisplayName("getByGenreTest")
	@Test
	@Order(3)
	public void getByGenreTest() {
		
		Map<String, Integer> genreMap = new HashMap<String, Integer>(); 
		String genreNames[] = {
				"poetry", "drama", "non-fiction", "literature",
				"comedy", "crime", "mystery", "romance"
		};
		genreMap.put("poetry", 0); 
		genreMap.put("drama", 0); 
		genreMap.put("non-fiction", 0); 
		genreMap.put("fiction", 0); 
		genreMap.put("literature", 0); 
		genreMap.put("comedy", 0); 
		genreMap.put("crime", 0); 
		genreMap.put("mystery", 0); 
		genreMap.put("romance", 0); 
		genreMap.put("young adult fiction", 0); 
		genreMap.put("historical", 0); 
		testPitches.forEach(p -> {
			String genreName = p.getGenre().getName();
			System.out.println("Name: " + genreName);
			genreMap.put(genreName, genreMap.get(genreName) + 1);
		});
		
		for (String name : genreNames) {
			Set<Pitch> byGenre = pitchDAO.getByGenre(name);
			assertTrue( byGenre.size() == genreMap.get(name) );
		}
	}
	
	
	@DisplayName("getByFormTest")
	@Test
	@Order(4)
	public void getByFormTest() {
		
		Map<String, Integer> formMap = new HashMap<String, Integer>(); 
		String formNames[] = {"novel", "novella", "short story", "article"};
		for (String name : formNames) { formMap.put(name, 0); }
		testPitches.forEach(p -> {
			String formName = p.getForm().getName();
			System.out.println("Name: " + formName);
			formMap.put(formName, formMap.get(formName) + 1);
		});
		
		for (String name : formNames) {
			Set<Pitch> byForm = pitchDAO.getByGenre(name);
			assertTrue( byForm.size() == formMap.get(name) );
		}
	}
	
	@DisplayName("getByStatusTest")
	@Test
	@Order(5)
	public void getByStatusTest() {
		Map<String, Integer> statusMap = new HashMap<String, Integer>(); 
		String statusNames[] = {"accepted", "rejected", 
								"pending-editor-review", "pending-author-review"};
		for (String name : statusNames) { statusMap.put(name, 0); }
		testPitches.forEach(p -> {
			String statusName = p.getStatus().getName();
			System.out.println("Name: " + statusName);
			statusMap.put(statusName, statusMap.get(statusName) + 1);
		});
		
		for (String name : statusNames) {
			Set<Pitch> byStatus = pitchDAO.getByStatus(name);
			assertTrue( byStatus.size() == statusMap.get(name) );
		}
	}
	
	@DisplayName("deleteTest")
	@Test
	@Order(6)
	public void deleteTest() {
		testPitches.forEach( pitch -> {
			assertTrue( pitchDAO.delete(pitch) );
		});
	}

}

/*
insert into stage (id, name) values
	(default, 'genre review'),
	(default, 'general review'),
	(default, 'senior review'),
	(default, 'final review'); 

insert into form (id, name, points) values
	(default, 'novel', 50),
	(default, 'novella', 25),
	(default, 'short story', 20),
	(default, 'article', 10);

*/