package com.cross.controllers;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cross.beans.Person;
import com.cross.beans.Pitch;
import com.cross.beans.Request;
import com.cross.services.RequestService;
import com.cross.services.RequestServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.javalin.http.Context;

@RestController 
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
@RequestMapping(path="/requests")
public class RequestController {
	
	private static RequestService requestServ;
	
	@Autowired
	public RequestController(RequestService r) {
		requestServ = r; 
	}

	
	public static void getRequests(Context ctx) {}
	public static void deleteRequest(Context ctx) {}
	public static void getRequestById(Context ctx) {}
	public static void getAllRequests(Context ctx) {}
	
    
	
	@PostMapping
	public ResponseEntity<Request> addRequest(@RequestBody Request r) {
		try {
			return ResponseEntity.ok( requestServ.addRequest(r) );
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
		}
	}
	
    @PutMapping
	public ResponseEntity<Request> updateRequest(@RequestBody Request r) {
		try {
			return ResponseEntity.status(200).build(); 
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
		}
		
	}
    
    
	public static void closeRequest(Context ctx) {
		
		Integer personId = Integer.parseInt( ctx.pathParam("id") ); 
		System.out.println(personId);
		ctx.status(200); 
		
	}
	
	@GetMapping(path="/personid/{id}")
	public ResponseEntity<Object[]> getRequestsByPersonId(@PathVariable Integer id) {		
		try {
			Object[] requests = requestServ.getRequestsByParticipantId(id).toArray();
			return ResponseEntity.ok(requests); 
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).build(); 
		}
	}

}



/*
 * 
 * 			// all requests to /cats go to this handler
			path ("api/requests", () -> {
				get(RequestController::getRequests); // get open requests is the default
				post(RequestController::addRequest); // add a request
				put(RequestController::updateRequest); // update a request
				path(":id", () -> {
					get(RequestController::getRequestById); // get a request by id
					delete(RequestController::deleteRequest); // delete a request by id
				});
				path ("personid/:id", () -> {
					get(RequestController::getRequestsByPersonId); // all open requests associated with a person

				});
				path ("all", () -> {
					get(RequestController::getAllRequests); // get all requests
				});
				path ("close/:id", () -> {
					put(RequestController::closeRequest); // close a request 
				});
			});
 * 
 * 
 * 
 */
