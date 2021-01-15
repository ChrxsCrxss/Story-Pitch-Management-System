package com.cross.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
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
	
	public DecisionController(DecisionService d) {
		decisionServ = d; 
	}
	private static Gson gson; 
	
	public static void initGsonBuilder() {
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	    gson = builder.create(); 
	}
	
	public static void addDecision(Context ctx) {
		System.out.println( ctx.body() );
	    
	    try {
		    Decision given;	    
		    given = gson.fromJson( ctx.body(), Decision.class);
		    Decision returned = decisionServ.add(given);
		    ctx.json( gson.toJson(returned) );
			ctx.status(200);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ctx.status(500);
	    }
	}
	
	public static void getDecisionsByPitchId(Context ctx) {
	    try {
			Integer id = Integer.parseInt( ctx.pathParam("id") ); 
			System.out.println(id);
			Object[] decisions = decisionServ.getDecisionsByPitchId(id).toArray();
		    ctx.json( gson.toJson(decisions) );
			ctx.status(200);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ctx.status(500);
	    }
	}
}
