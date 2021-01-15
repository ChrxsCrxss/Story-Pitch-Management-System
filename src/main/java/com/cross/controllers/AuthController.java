package com.cross.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cross.beans.Person;
import com.cross.beans.Role;
import com.cross.services.PersonService;
import com.cross.services.PersonServiceImpl;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;

import io.javalin.http.Context;

@RestController 
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
@RequestMapping(path="/users")
public class AuthController {
	
	private static PersonService personServ = new PersonServiceImpl();
	private static Gson gson; 
	
	@Autowired
	public AuthController(PersonService p) {
		personServ = p; 
	}
	
	
	@PostMapping(path="/login")
	public ResponseEntity<Person> login(@RequestBody Person p) {
		

	    Person queriedPerson = personServ.getPersonByUsername(p.getUsername());
	    
	    if ( queriedPerson != null ) {
	    	if (queriedPerson.getPassword().equals(p.getPassword()) ) {
		    	return ResponseEntity.ok(queriedPerson);
	    	}
	    	return ResponseEntity.badRequest().build();
	    }
		return ResponseEntity.notFound().build(); 
				
		

	};
	
	@PostMapping(path="/sigup")
	public ResponseEntity<Person> signup(@RequestBody Person p){
		try {
			return ResponseEntity.ok( personServ.addPerson(p) );
		}
		catch(Exception e){
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
		}
	}

}
