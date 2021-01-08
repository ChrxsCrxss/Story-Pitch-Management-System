package com.cross.controllers;

import com.cross.beans.Comment;
import com.cross.beans.Pitch;
import com.cross.services.CommentService;
import com.cross.services.CommentServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.javalin.http.Context;


public class CommentController {
	
	private static CommentService commentServ = new CommentServiceImpl(); 
	private static Gson gson; 
	
	public static void initGsonBuilder() {
		GsonBuilder builder = new GsonBuilder(); 
	    builder.setPrettyPrinting(); 
	    gson = builder.create(); 
	}
	
	public static void getByRequestId(Context ctx) {
		try {
			Integer id = Integer.parseInt( ctx.pathParam("id") ); 
			Object[] pitches = commentServ.getCommentsByRequestId(id).toArray();
		    ctx.json( gson.toJson(pitches) );
			ctx.status(200);
			
		} catch (Exception e) {
			e.printStackTrace();
			ctx.status(500);
		}
	}
	
	public static void addComment(Context ctx) {
		System.out.println( ctx.body() );
	    try {
		    Comment given, returned;     
		    given = gson.fromJson( ctx.body(), Comment.class);
		    returned = commentServ.add(given);
		    ctx.json( gson.toJson(returned));
			ctx.status(200);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	ctx.status(500);
	    }
	}
	
	
}
