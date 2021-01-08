package com.cross.controllers;

import com.cross.beans.Person;
import com.cross.beans.Role;
import com.cross.services.PersonService;
import com.cross.services.PersonServiceImpl;
import com.google.gson.Gson; 
import com.google.gson.GsonBuilder;

import io.javalin.http.Context;

public class AuthController {
	
	private static PersonService personServ = new PersonServiceImpl();
	private static Gson gson; 
	
	public static void initGsonBuilder() {
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	    gson = builder.create(); 
	}
	
	
	public static void login(Context ctx) {
		
		System.out.println( ctx.body() );
	    Person deserializedPerson, queriedPerson;  
	    
	    deserializedPerson = gson.fromJson( ctx.body(), Person.class);
	    queriedPerson = personServ.getPersonByUsername(deserializedPerson.getUsername());
	    
	    if ( queriedPerson == null ) {
	    	ctx.result("No user with that password found"); 
	    	ctx.status(404);
	    }
	    
	    if ( queriedPerson.getPassword().equals(deserializedPerson.getPassword() )) {
	    	ctx.result("No combination of that username and password found");
	    	ctx.status(404);
	    }
	    
	    // TODO : be sure to scrub the password before sending the Person object back to the client
	    
	    queriedPerson.setPassword("******");
	    
	    try {
		    ctx.json( gson.toJson(queriedPerson) );
			ctx.status(200);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ctx.status(500);
	    }
		
	};
	
	public static void logOut(Context ctx) {
		 
		ctx.req.getSession().invalidate();
	}
	
	public static void registerUser(Context ctx){
	    Person newPerson = gson.fromJson( ctx.body(), Person.class); 
		try {
//			personServ.addPerson(newPerson);
		}
		catch(Exception e){
			e.printStackTrace();
			ctx.status(409);
		}
		ctx.status(200);
	}

}
