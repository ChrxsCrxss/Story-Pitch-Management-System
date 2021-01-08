package com.cross.data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cross.beans.Decision;
import com.cross.beans.DecisionType;
import com.cross.beans.Draft;
import com.cross.beans.Form;
import com.cross.beans.Person;
import com.cross.beans.Pitch;
import com.cross.beans.Stage;
import com.cross.beans.Status;
import com.cross.exceptions.InvalidGeneralEditorException;
import com.cross.exceptions.UnknownDecisionTypeException;
import com.cross.exceptions.UnknownFormException;
import com.cross.exceptions.UnknownStageException;
import com.cross.beans.Decision;
import com.cross.utils.HibernateUtil;

public class DecisionHibernate implements DecisionDAO {

	private HibernateUtil hu = HibernateUtil.getHibernateUtil();
	private PitchDAO pitchDAO = new PitchHibernate(); 
	private PersonDAO personDAO = new PersonHibernate(); 
	private DraftDAO draftDAO = new DraftHibernate(); 
	
	@Override
	public Decision getById(Integer id) {
		Session s = hu.getSession();
		Decision c = s.get(Decision.class, id);
		s.close();
		return c;
	}

	@Override
	public Set<Decision> getAll() {
		Session s = hu.getSession();
		String query = "FROM Decision";
		Query<Decision> q = s.createQuery(query, Decision.class);
		List<Decision> decisionsList = q.getResultList();
		Set<Decision> decisionsSet = new HashSet<>();
		decisionsSet.addAll(decisionsList);
		s.close();
		return decisionsSet;
	}
	
	@Override
	public boolean update(Decision t) {
		Boolean didUpdate = false; 
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.update(t);
			tx.commit();
			didUpdate = true; 
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				didUpdate = false; 
			}
		} finally {
			s.close();
		}
		return didUpdate; 
	}
	
	private Boolean isSeniorEditor(Person p) {
		return p.getRole().getName().equalsIgnoreCase("SENIOR EDITOR");
	}
	
	private Boolean isApproval(Decision d) {
		 return d.getDecisionType().getName().endsWith("approval"); 
	}
	
	private Boolean isRejection(Decision d) {
		return d.getDecisionType().getName().endsWith("rejection");
	}
	
	private Boolean hasPrevSeniorApproval(Set<Decision> dSet) {
		
		for (Decision d : dSet) {
			Person p = personDAO.getById( d.getEditorId() );
			if ( isApproval(d) && isSeniorEditor(p) ) {
				return true; 
			}	
		}
		return false; 
	}
	
	private void approvePitchAndDraft(Pitch p, Draft d, Session s) {
		p.setStatus( UtilityDAO.getByName(new Status(), "approved"));
		d.setStatus( UtilityDAO.getByName(new Status(), "approved"));
		Person author = personDAO.getById( p.getAuthorId() );
		Integer pts = author.getPoints() + p.getForm().getPoints(); 
		author.setPoints( pts );
		s.update(p);
		s.update(d);
		s.update(author);
	}
	
	private void rejectPitchAndDraft(Pitch p, Draft d, Session s) {
		p.setStatus( UtilityDAO.getByName(new Status(), "rejected"));
		d.setStatus( UtilityDAO.getByName(new Status(), "rejected"));
		Person author = personDAO.getById( p.getAuthorId() );
		Integer pts = author.getPoints() + p.getForm().getPoints();
		author.setPoints( pts );
		s.update(p);
		s.update(d);
		s.update(author);
	}
	
	private Boolean hasMajorityVote() {
		return false; 
	}
		
	@Override
	public Decision add(Decision d) throws Exception {
		
		Session s = hu.getSession(); 
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			Pitch pitch = pitchDAO.getById( d.getPitchId() );
			pitch.setLastModifiedTime( LocalDateTime.now() );
			Person editor = personDAO.getById( d.getEditorId() );
			Set<Decision> prevDecisions = getByPitchId(d.getPitchId());
			Set<Person> genreCommittee = personDAO.getAll();
			genreCommittee.removeIf( p -> ! p.getGenres().removeIf( 
					g -> g.getName().equalsIgnoreCase(pitch.getGenre().getName() )) );


			// Approval cases
			if ( isApproval(d) ) {

				switch ( pitch.getStage().getName().toUpperCase() ) {
				case "GENRE REVIEW":
					pitch.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
					pitch.setStage( UtilityDAO.getByName(new Stage(), "general review"));
					s.update(pitch); 
					break;
				case "GENERAL REVIEW":
					if ( editor.getGenres().removeIf(g -> g.getId() == pitch.getGenre().getId() ) ) {
						 throw new InvalidGeneralEditorException(); 
					} else {
						pitch.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
						pitch.setStage( UtilityDAO.getByName(new Stage(), "senior review"));
						s.update(pitch); 
					}
					break;
				case "SENIOR REVIEW":
					pitch.setStatus( UtilityDAO.getByName(new Status(), "pending-editor-review"));
					pitch.setStage( UtilityDAO.getByName(new Stage(), "final review"));
					s.update(pitch); 
					break;
				case "FINAL REVIEW":
					
					Draft draft = draftDAO.getByPitchId( d.getPitchId() );
					
					switch ( pitch.getForm().getName().toUpperCase() ) {
					/**
					 * If a pitch in final review with status pending-author-review receives 
					 * an approval, it must have come from someone in the genre committee 
					 * besides the approving senior editor 
					 */
					case "ARTICLE": 
						if ( isSeniorEditor(editor) ) {
							approvePitchAndDraft(pitch, draft, s);
						}
						break; 
					/**
					 * For short stories, we need to consider previous approvals, so we filter the 
					 * previous decisions. If the current deciding editor is a senior editor, and there
					 * has been atleast one previous draft approval, the pitch/draft is approved. If the
					 * current deciding editor is a senior editor and the only member of the genre 
					 * committee, the pitch/draft is approved. If there has been alteast one previous
					 * approval from a senior editor, the draft/pitch is approved. Else, the decision is
					 * saved and the draft and pitch statuses remaining pending.
					 */
					case "SHORT STORY": 
						prevDecisions.removeIf( p -> ! p.getDecisionType().getName().equalsIgnoreCase("draft-approval") );
						
						if ( isSeniorEditor(editor) && prevDecisions.size() > 0 ) {
							approvePitchAndDraft(pitch, draft, s);
						} else if ( hasPrevSeniorApproval(prevDecisions) ) {
							approvePitchAndDraft(pitch, draft, s);
						} else { /* the draft and pitch statuses remaining pending */ }
						break; 
					/*
					 * For novellas and novels, simply majority vote determines. 
					 */
					case "NOVELLA":
					case "NOVEL":
						/**
						 * Double prevDecisions.size() 
						 */
						
						System.out.println("Majority calc stats:");
						System.out.println(prevDecisions.size() + 1);
						System.out.println(genreCommittee.size()); 	
						prevDecisions.removeIf( p -> ! p.getDecisionType().getName().equalsIgnoreCase("draft-approval") );
						
						if (   ( (prevDecisions.size() + 1) / (double) genreCommittee.size() ) > 0.5 ) {
							approvePitchAndDraft(pitch, draft, s);
						}
						break;
					default: 
						throw new UnknownFormException();
					} 
					break;
					default: 
						throw new UnknownStageException(); 
				}

			} else if (  isRejection(d) ) {
				
				switch ( pitch.getStage().getName().toUpperCase() ) {
				case "GENRE REVIEW":
					pitch.setStatus( UtilityDAO.getByName(new Status(), "rejected"));
					s.update(pitch); 
					break;
				case "GENERAL REVIEW":
					if ( editor.getGenres().removeIf(g -> g.getId() == pitch.getGenre().getId() ) ) {
						throw new InvalidGeneralEditorException(); 
					} else {
						pitch.setStatus( UtilityDAO.getByName(new Status(), "rejected"));
						s.update(pitch); 
					}
					break;
				case "SENIOR REVIEW":
					pitch.setStatus( UtilityDAO.getByName(new Status(), "rejected"));
					s.update(pitch); 
					break;
				case "FINAL REVIEW":
					
					Draft draft = draftDAO.getByPitchId( d.getPitchId() );
					
					switch ( pitch.getForm().getName().toUpperCase() ) {
					case "ARTICLE": 
						if ( isSeniorEditor(editor) ) {
							rejectPitchAndDraft(pitch, draft, s);
						}
						break; 
					/**
					 * For short stories, we need to consider previous approvals, so we filter the 
					 * previous decisions. If the current deciding editor is a senior editor, and there
					 * has been atleast one previous draft approval, the pitch/draft is approved. If the
					 * current deciding editor is a senior editor and the only member of the genre 
					 * committee, the pitch/draft is approved. If there has been alteast one previous
					 * approval from a senior editor, the draft/pitch is approved. Else, the decision is
					 * saved and the draft and pitch statuses remaining pending.
					 */
					case "SHORT STORY": 
						prevDecisions.removeIf( p -> ! p.getDecisionType().getName().equalsIgnoreCase("draft-rejection") );
						
						if ( isSeniorEditor(editor) && prevDecisions.size() > 0 ) {
							rejectPitchAndDraft(pitch, draft, s);
						} else if ( hasPrevSeniorApproval(prevDecisions) ) {
							rejectPitchAndDraft(pitch, draft, s);
						} else { /* the draft and pitch statuses remaining pending */ }
						break; 
					/*
					 * For novellas and novels, simply majority vote determines. 
					 */
					case "NOVELLA":
					case "NOVEL":
						prevDecisions.removeIf( p -> ! p.getDecisionType().getName().equalsIgnoreCase("draft-rejection") );
						if (  ( (prevDecisions.size() + 1) / (double) genreCommittee.size() ) > 0.5 ) {
							rejectPitchAndDraft(pitch, draft, s);
						}
						break;
					default: 
						throw new UnknownFormException();
					} 
					break;
					default: 
						throw new UnknownStageException(); 
				}
			} else {
				throw new UnknownDecisionTypeException(); 
			}
			s.save(d);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) { tx.rollback(); }
			e.printStackTrace();
			throw e; 
		}
		return d; 
	}
	
	@Override
	public boolean delete(Decision t) {
		Boolean didDelete = false; 
		Session s = hu.getSession();
		Transaction tx = null;
		try {
			tx = s.beginTransaction();
			s.delete(t);
			tx.commit();
			didDelete = true; 
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
				didDelete = false; 
			}
		} finally {
			s.close();
		}
		return didDelete; 
	}
	

	@Override
	public Set<Decision> getByEditorId(Integer editorId) {

		Session s = hu.getSession();
		String query = "FROM Decision where editor_id = :id";
		Query<Decision> q = s.createQuery(query, Decision.class);
		q.setParameter("id", editorId);
		List<Decision> decisionsList = q.getResultList();
		Set<Decision> decisionsSet = new HashSet<>();
		decisionsSet.addAll(decisionsList);
		s.close();
		return decisionsSet;
	}
	
	@Override
	public Set<Decision> getByPitchId(Integer pitchId) {

		Session s = hu.getSession();
		String query = "FROM Decision where pitch_id = :id";
		Query<Decision> q = s.createQuery(query, Decision.class);
		q.setParameter("id", pitchId);
		List<Decision> decisionsList = q.getResultList();
		Set<Decision> decisionsSet = new HashSet<>();
		decisionsSet.addAll(decisionsList);
		s.close();
		return decisionsSet;
	}	
	
}

