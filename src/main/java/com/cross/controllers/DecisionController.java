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

import com.cross.beans.Decision;
import com.cross.services.DecisionService;
import com.cross.services.DecisionServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.javalin.http.Context;

@RestController 
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
@RequestMapping(path="/decisions")
public class DecisionController {
	
	private static DecisionService decisionServ;
	
	@Autowired
	public DecisionController(DecisionService d) {
		decisionServ = d; 
	}

	@PostMapping
	public ResponseEntity<Decision> addDecision(@RequestBody Decision d) {
	    try {
		    return ResponseEntity.ok( decisionServ.add(d) );
	    } catch (Exception e) {
	    	return ResponseEntity.status(500).build(); 
	    }
	}
	
	@GetMapping(path="pitchid/{id}")
	public ResponseEntity<Object[]> getDecisionsByPitchId(@PathVariable Integer id) {
	    try {
			Object[] decisions = decisionServ.getDecisionsByPitchId(id).toArray();
			return ResponseEntity.ok(decisions); 
	    } catch (Exception e) {
	    	return ResponseEntity.status(500).build();
	    }
	}
}
