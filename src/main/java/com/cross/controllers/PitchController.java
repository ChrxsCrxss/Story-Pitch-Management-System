package com.cross.controllers;

import static io.javalin.apibuilder.ApiBuilder.delete;
import static io.javalin.apibuilder.ApiBuilder.get;
import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;
import static io.javalin.apibuilder.ApiBuilder.put;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	private static Gson gson; 
	
	public static void initGsonBuilder() {
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	    gson = builder.create(); 
	}
	
	public static void getPendingPitches(Context ctx) { /* TODO : implement */ }
	public static void getPitchById(Context ctx) { /* TODO : implement */ }
	public static void getAllPitches(Context ctx) { /* TODO : implement */ }

	
	
	public static void updatePitch(Context ctx) {
		try {
		    Pitch updatedPitch;	    
		    updatedPitch = gson.fromJson( ctx.body(), Pitch.class);
		    
		    Boolean didUpdate = pitchServ.updatePitch(updatedPitch); 
		    System.out.println(didUpdate);
		    if (didUpdate) {
		    	ctx.status(200);
		    } else {
		    	ctx.status(500);
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}
	
	
	public static void getPitchByGenre(Context ctx) {  
		
		try {
			String genre = ctx.pathParam("genre");
			Object[] pitches = pitchServ.getPitchesByGenre(genre).toArray();
		    ctx.json( gson.toJson(pitches) );
			ctx.status(200);
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}
	
	public static void getPitchByGeneralEditorId(Context ctx) {  
		
		try {
			Integer id = Integer.parseInt( ctx.pathParam("id") ); 
			Object[] pitches = pitchServ.getPitchesByGeneralEditorId(id).toArray();
		    ctx.json( gson.toJson(pitches) );
			ctx.status(200);
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}
	
	public static void deletePitch(Context ctx) { 
		
		Integer id = Integer.parseInt( ctx.pathParam("id") ); 
		System.out.println(id); 
		ctx.status(200); 
	}
	
	public static void addPitch(Context ctx) {
		
		System.out.println( ctx.body() );
	    
	    try {
		    Pitch deserializedPitch;	    
		    deserializedPitch = gson.fromJson( ctx.body(), Pitch.class);
		    Pitch outPitch = pitchServ.addPitch(deserializedPitch);
		    ctx.json( gson.toJson(outPitch) );
			ctx.status(200);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ctx.status(500);
	    }
	
	}
	
	public static void getPitchByAuthorId(Context ctx) { 		
	    try {
			Integer id = Integer.parseInt( ctx.pathParam("id") ); 
			System.out.println(id);
			Object[] pitches = pitchServ.getPitchesByAuthorId(id).toArray();
		    ctx.json( gson.toJson(pitches) );
			ctx.status(200);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ctx.status(500);
	    }
	}
}