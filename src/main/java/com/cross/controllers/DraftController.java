package com.cross.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cross.beans.Draft;
import com.cross.services.DraftService;
import com.cross.services.DraftServiceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.javalin.http.Context;

@RestController 
@CrossOrigin(origins="http://localhost:4200", allowCredentials="true")
@RequestMapping(path="/drafts")
public class DraftController {
	
	private static DraftService draftServ;
	
	@Autowired
	public DraftController(DraftService d) {
		draftServ = d; 
	}
	private static Gson gson; 
	
	public static void initGsonBuilder() {
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	    gson = builder.create(); 
	}
	
	public static void addDraft(Context ctx) {
		System.out.println( ctx.body() );
	    try {
		    Draft given, returned;     
		    given = gson.fromJson( ctx.body(), Draft.class);
		    returned = draftServ.add(given);
		    ctx.json( gson.toJson(returned));
			ctx.status(200);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ctx.status(500);
	    }
	}; 
	
	public static void getDraftByPitchId(Context ctx) {
	    try {
			Integer id = Integer.parseInt( ctx.pathParam("id") ); 
			System.out.println(id);
			Draft draft = draftServ.getDraftByPitchId(id);
		    ctx.json( gson.toJson(draft) );
			ctx.status(200);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ctx.status(500);
	    }
	}; 
	public static void updateDraft(Context ctx) {
		try {
		    Draft updatedDraft;	    
		    updatedDraft = gson.fromJson( ctx.body(), Draft.class);
		    
		    Boolean didUpdate = draftServ.updateDraft(updatedDraft); 
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
	}; 
	
}
