package com.cross.controllers;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	private static Gson gson; 
	
	public static void initGsonBuilder() {
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	    gson = builder.create(); 
	}
	
	public static void getRequests(Context ctx) {}
	public static void deleteRequest(Context ctx) {}
	public static void getRequestById(Context ctx) {}
	public static void getAllRequests(Context ctx) {}
	
    
	public static void addRequest(Context ctx) {
	    Request request;
	    request = gson.fromJson( ctx.body(), Request.class);
	    System.out.println(request.getId());
	    System.out.println(request.getStatus().getName());
	    ctx.status(200); 
	    
		try {
		    Request given;
		    given = gson.fromJson( ctx.body(), Request.class);
			Request returned = requestServ.addRequest(given);
		    ctx.json( gson.toJson(returned) );
			ctx.status(200);
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}
	
    
	public static void updateRequest(Context ctx) {
		
	    Request request;
	    request = gson.fromJson( ctx.body(), Request.class);
	    System.out.println(request.getId());
	    System.out.println(request.getStatus().getName());
	    ctx.status(200); 
		
		
	}
    
    
	public static void closeRequest(Context ctx) {
		
		Integer personId = Integer.parseInt( ctx.pathParam("id") ); 
		System.out.println(personId);
		ctx.status(200); 
		
	}
	
	public static void getRequestsByPersonId(Context ctx) {		
		try {
			Integer id = Integer.parseInt( ctx.pathParam("id") ); 
			System.out.println(id);
			Object[] requests = requestServ.getRequestsByParticipantId(id).toArray();
		    ctx.json( gson.toJson(requests) );
			ctx.status(200);
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}

}
