package com.cross.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cross.beans.Comment;
import com.cross.beans.Pitch;
import com.cross.services.CommentService;
import com.cross.services.CommentServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.javalin.http.Context;

@RestController 
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
@RequestMapping(path="/comments")
public class CommentController {
	
	private static CommentService commentServ; 
	
	@Autowired 
	public CommentController(CommentService c) {
		commentServ = c; 
	}
	
	@GetMapping(path="requestid/{id}")
	public ResponseEntity<Object[]> getByRequestId(@PathVariable Integer id) {
		try {
			Object[] pitches = commentServ.getCommentsByRequestId(id).toArray();
			return ResponseEntity.ok(pitches); 
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
		}
	}
	
	@PostMapping
	public ResponseEntity<Comment> addComment(@RequestBody Comment c) {
	    try {
		    Comment returnedComment = commentServ.add(c);
		    return ResponseEntity.ok(returnedComment);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return ResponseEntity.status(500).build(); 
	    }
	}
		
}
