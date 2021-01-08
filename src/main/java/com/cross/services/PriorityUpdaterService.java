package com.cross.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import com.cross.beans.Pitch;
import com.cross.beans.Priority;
import com.cross.data.PitchDAO;
import com.cross.data.PitchHibernate;
import com.cross.data.UtilityDAO;

public class PriorityUpdaterService implements Runnable {
	
	private PitchDAO pitchDAO; 
	private Long SLEEP_PERIOD = (long) 3600000; 
	private static PriorityUpdaterService priorityUpdaterService;
	
	private PriorityUpdaterService() {
		pitchDAO = new PitchHibernate(); 
	}
	
	public static PriorityUpdaterService getPriorityUpdaterService() {
		if (priorityUpdaterService == null ) {
			priorityUpdaterService = new PriorityUpdaterService();
		}
		return priorityUpdaterService;
	}
	
	@Override
	public void run() {
		
		System.out.println("Starting PriorityUpdaterService");
		while (true) {
			
			try {
				Thread.sleep(SLEEP_PERIOD);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Set<Pitch> pitches = pitchDAO.getAll(); 
			
			pitches.forEach(pitch -> {
				LocalDateTime lastmdtime = pitch.getLastModifiedTime(); 
				Long elapsedTime = lastmdtime.until( LocalDateTime.now(), ChronoUnit.MILLIS);
				if (elapsedTime > SLEEP_PERIOD) {
					pitch.setPriority(UtilityDAO.getByName(new Priority(), "high"));
					pitchDAO.update(pitch);
				}
			});
		}
	}
}
