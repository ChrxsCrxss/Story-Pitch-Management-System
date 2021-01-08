package com.cross.data;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.cross.beans.Comment;
import com.cross.beans.Decision;
import com.cross.data.CommentDAO;
import com.cross.data.CommentHibernate;
import com.cross.utils.StringGenerator;


public class CommentHibernateTest {
	
	private static CommentDAO commentDAO = new CommentHibernate(); 
	private static Set<Comment> testComments = new HashSet<Comment>(); 
	
	private static void generateTestComments() {
		Random rand = new Random(); 
		for (int i = 0; i < 200; ++i) {
			Comment c = new Comment(); 
			c.setCommentorId( 1 + rand.nextInt(3) );
			c.setRequestId(1 + rand.nextInt(20) );
			c.setContent( StringGenerator.randomString(5) );
			c.setCreationTime( LocalDateTime.now() );
			testComments.add(c); 
		}
	}
	
	@DisplayName("addTest")
	@Test
	@Order(1) 
	public void addTest() {
		generateTestComments(); 
		testComments.forEach( c -> {
			Comment cmt = null; 
			cmt = commentDAO.add(c);
			assertTrue(cmt != null);
			c.setId( cmt.getId());
		});	
	}
	
	@DisplayName("getByRequestIdTest")
	@Test
	@Order(2)
	public void getByRequestIdTest() {
		Map<Integer, Integer> idMap = new HashMap<Integer, Integer>(); 
		List<Integer> rqIds = IntStream.rangeClosed(1, 20)
			    .boxed().collect(Collectors.toList());
		
		for (Integer id : rqIds) { idMap.put(id, 0); }
		testComments.forEach(c -> {
			Integer rqId = c.getRequestId(); 
			idMap.put(rqId, idMap.get(rqId) + 1);
		});
		
		for (Integer id : rqIds) {
			Set<Comment> byRqId = commentDAO.getByRequestId(id);
			assertTrue( byRqId.size() == idMap.get(id) );
		}
	}
	
	
	@DisplayName("deleteTest")
	@Test
	@Order(3)
	public void deleteTest() {
		testComments.forEach( c -> {
			assertTrue( commentDAO.delete(c) );
		});
	}

}
