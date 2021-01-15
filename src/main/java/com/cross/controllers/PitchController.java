package com.cross.controllers;


import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cross.beans.Form;
import com.cross.beans.Genre;
import com.cross.beans.Person;
import com.cross.beans.Pitch;
import com.cross.beans.Status;
import com.cross.services.PitchService;
import com.cross.services.PitchServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.javalin.http.Context;

@RestController 
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
@RequestMapping(path="/ptiches")
public class PitchController {
	
	private static PitchService pitchServ;
	
	@Autowired
	public PitchController(PitchService p) {
		pitchServ = p;
	}
	
	public static void getPendingPitches(Context ctx) { /* TODO : implement */ }
	public static void getPitchById(Context ctx) { /* TODO : implement */ }
	public static void getAllPitches(Context ctx) { /* TODO : implement */ }

	
	@PutMapping(path="/{id}")
	public ResponseEntity<Object> updatePitch(@RequestBody Pitch p ) {
		try {
		    Boolean didUpdate = pitchServ.updatePitch(p); 
		    System.out.println(didUpdate);
		    if (didUpdate) {
		    	return ResponseEntity.ok().build(); 
		    } else {
		    	return ResponseEntity.status(500).build(); 
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
		}
	}
	
	@GetMapping(path="/genre/{genre}")
	public ResponseEntity<Object[]> getPitchByGenre(@PathVariable String genre) {  
		
		try {
			Object[] pitches = pitchServ.getPitchesByGenre(genre).toArray(); 
			return ResponseEntity.ok(pitches);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
		}
	}
	
	@GetMapping(path="/generaleditor/{id}")
	public ResponseEntity<Object[]> getPitchByGeneralEditorId(@PathVariable Integer id) {  
		
		try {
			Object[] pitches = pitchServ.getPitchesByGeneralEditorId(id).toArray();
			return ResponseEntity.ok(pitches);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
		}
	}
	
	@DeleteMapping(path="/{id}")
	public ResponseEntity<Boolean> deletePitch(@PathVariable Integer id) { 
		try {
			pitchServ.deletePitch( pitchServ.getPitchById(id) );
			return ResponseEntity.ok(null); 
		} catch (Exception e) {
			return ResponseEntity.status(500).build();
		}
	}
	
	@PostMapping
	public ResponseEntity<Pitch> addPitch(@RequestBody Pitch p) {
	    try {
	    	return ResponseEntity.ok( pitchServ.addPitch(p) );
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	return ResponseEntity.status(500).build(); 
	    }
	
	}
	
	@GetMapping(path="/authorid/{id}")
	public ResponseEntity<Object[]>  getPitchByAuthorId(@PathVariable Integer id) { 		
	    try {
			Object[] pitches = pitchServ.getPitchesByAuthorId(id).toArray();
			return ResponseEntity.ok(pitches);
	    } catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
	    }
	}
}

