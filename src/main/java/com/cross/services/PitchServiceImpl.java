package com.cross.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import static java.util.Collections.shuffle; 

import com.cross.beans.Form;
import com.cross.beans.Genre;
import com.cross.beans.Person;
import com.cross.beans.Pitch;
import com.cross.beans.Priority;
import com.cross.beans.Stage;
import com.cross.beans.Status;
import com.cross.data.PitchDAO;
import com.cross.data.PitchHibernate;
import com.cross.data.UtilityDAO;

public class PitchServiceImpl implements PitchService {
	
	private PitchDAO pitchDAO; 
	private PersonService personServ;  
	
	public PitchServiceImpl() {
		pitchDAO = new PitchHibernate(); 
		personServ = new PersonServiceImpl(); 
	}
	
	@Override
	public Set<Pitch> getPitchesByAuthorId(Integer id) {
		return pitchDAO.getByAuthorId(id);
	}

	@Override
	public Pitch addPitch(Pitch p) {
		
		// Timestamp pitch
		LocalDateTime now = LocalDateTime.now(); 
		p.setCreatedTime(now);
		p.setLastModifiedTime(now);
		p.setDeadline( now.plusDays(30));
		
		String formName = p.getForm().getName();
		String genreName = p.getGenre().getName();
		p.getForm().setId( UtilityDAO.getByName( new Form(), formName ).getId() );
		p.getGenre().setId( UtilityDAO.getByName( new Genre(), genreName ).getId() );
		
		// Newly-created pitches have pending-editor-review status and
		// low priority
		if ( p.getStatus() == null ) {
			p.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
		}
		p.setPriority( UtilityDAO.getByName(new Priority(), "low"));
		
		/*
		 * Now we need to determine the stage and general editor. If there are
		 * assistant editors in the pitch's genre, the pitch enters genre review,
		 * otherwise general review. If an editor is not in the pitch's genre, 
		 * that editor is assigned as the general editor of the pitch. We convert
		 * to a list and shuffle to get a uniform distribution of general editors.
		 */
		Set<Person> persons = personServ.getAll(); 		
		persons.removeIf( pr -> pr.getRole().getName().equalsIgnoreCase("AUTHOR") ); 
		List<Person> editors = new ArrayList<Person>(); 
		editors.addAll(persons);
		shuffle(editors);
		
		Genre pitchGenre = UtilityDAO.getByName( new Genre(), genreName);
		for (Person ed : editors) {
			Boolean isInGenre = ed.getGenres().removeIf( g -> g.getName().equalsIgnoreCase(genreName) );
			Boolean isAssistant = ed.getRole().getName().equalsIgnoreCase("ASSISTANT EDITOR");
			// By checking if stage is null, we can avoid an unnecessary database query
			if ( p.getStage() == null && isInGenre && isAssistant ) {
				p.setStage( UtilityDAO.getByName( new Stage(), "genre review"));
			} else if ( ! isInGenre ) {
				p.setGeneralEditorId(ed.getId());
			}
			
			// Attempt to break out of loop early if we can determine stage
			// and general editor
			if ( p.getStage() != null && p.getGeneralEditorId() != null ) {
				break; 
			}
		}
		
		if (p.getStage() == null) {
			p.setStage( UtilityDAO.getByName( new Stage(), "general review"));
		}
		
		return pitchDAO.add(p);
	}
	
	/*
	 *    let pitch = {
        id: 0,
        authorId: currentUser.id,
        tagline: document.getElementById('input-tagline-form').value,
        title: document.getElementById('input-title-form').value,
        genre: { id: -1, name: document.getElementById('input-genre-form').value },
        form: { id: -1, name: document.getElementById('input-form-form').value }
    }
	 * 
	 */

	@Override
	public Set<Pitch> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pitch getPitchById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updatePitch(Pitch p) {
		return pitchDAO.update(p);
	}

	@Override
	public boolean deletePitch(Pitch p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<Pitch> getPitchesByGenre(String genre) {
		return pitchDAO.getByGenre(genre);
	}

	@Override
	public Set<Pitch> getPitchesByGeneralEditorId(Integer id) {
		return pitchDAO.getByGeneralEditorId(id);
	}

}
