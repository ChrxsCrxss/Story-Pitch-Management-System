package com.cross.data;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.cross.beans.DecisionType;
import com.cross.beans.Form;
import com.cross.beans.Genre;
import com.cross.beans.Person;
import com.cross.beans.Priority;
import com.cross.beans.Role;
import com.cross.beans.Stage;
import com.cross.beans.Status;
import com.cross.utils.HibernateUtil;

/*
 * For some as-yet uninvestigated reason, Hibernate maps the id of
 * composite objects, but not other values, such as name. As quick 
 * and dirty workout around is to declare a bunch of overloaded 
 * methods to lookup these composite objects. 
 * 
 */
public class UtilityDAO {
	
	private static HibernateUtil hu = HibernateUtil.getHibernateUtil();
	
	
	// Overloaded methods to get form data
	public static Form getById(Form o, Integer id) {
		Session s = hu.getSession();
		Form f = s.get(Form.class, id);
		s.close();
		return f;
	}
	public static Form getByName(Form o, String name) {
		return (Form) hu.getSession()
						.createQuery( "from Form where name = :name")
						.setParameter("name", name)
						.getSingleResult();
	}
	
	public static Set<Form> getAll(Form o) {
		Session s = hu.getSession();
		String query = "FROM Form";
		Query<Form> q = s.createQuery(query, Form.class);
		List<Form> formsList = q.getResultList();
		Set<Form> formsSet = new HashSet<>();
		formsSet.addAll(formsList);
		s.close();
		return formsSet;
	}
	
	// Overloaded methods to get role data
	public static Role getById(Role o, Integer id) {
		Session s = hu.getSession();
		Role f = s.get(Role.class, id);
		s.close();
		return f;
	}
	public static Role getByName(Role o, String name) {
		return (Role) hu.getSession()
				.createQuery( "from Role where name = :name")
				.setParameter("name", name)
				.getSingleResult();
	}
	
	public static Set<Role> getAll(Role o) {
		Session s = hu.getSession();
		String query = "FROM Role";
		Query<Role> q = s.createQuery(query, Role.class);
		List<Role> rolesList = q.getResultList();
		Set<Role> rolesSet = new HashSet<>();
		rolesSet.addAll(rolesList);
		s.close();
		return rolesSet;
	}
	
	// Overloaded methods to get status data
	public static Status getById(Status o, Integer id) {
		Session s = hu.getSession();
		Status f = s.get(Status.class, id);
		s.close();
		return f;
	}
	public static Status getByName(Status o, String name) {
		return (Status) hu.getSession()
				.createQuery( "from Status where name = :name")
				.setParameter("name", name)
				.getSingleResult();
	}
	public static Set<Status> getAll(Status o) {
		Session s = hu.getSession();
		String query = "FROM Status";
		Query<Status> q = s.createQuery(query, Status.class);
		List<Status> statussList = q.getResultList();
		Set<Status> statussSet = new HashSet<>();
		statussSet.addAll(statussList);
		s.close();
		return statussSet;
	}
	
	// Overloaded methods to get genre data
	public static Genre getById(Genre o, Integer id) {
		Session s = hu.getSession();
		Genre f = s.get(Genre.class, id);
		s.close();
		return f;
	}
	public static Genre getByName(Genre o, String name) {
		return (Genre) hu.getSession()
				.createQuery( "from Genre where name = :name")
				.setParameter("name", name)
				.getSingleResult();
	}
	public static Set<Genre> getAll(Genre o) {
		Session s = hu.getSession();
		String query = "FROM Genre";
		Query<Genre> q = s.createQuery(query, Genre.class);
		List<Genre> genresList = q.getResultList();
		Set<Genre> genresSet = new HashSet<>();
		genresSet.addAll(genresList);
		s.close();
		return genresSet;
	}
	
	// Overloaded methods to get priority data
	public static Priority getById(Priority o, Integer id) {
		Session s = hu.getSession();
		Priority f = s.get(Priority.class, id);
		s.close();
		return f;
	}
	public static Priority getByName(Priority o, String name) {
		return (Priority) hu.getSession()
				.createQuery( "from Priority where name = :name")
				.setParameter("name", name)
				.getSingleResult();
	}
	public static Set<Priority> getAll(Priority o) {
		Session s = hu.getSession();
		String query = "FROM Priority";
		Query<Priority> q = s.createQuery(query, Priority.class);
		List<Priority> prioritysList = q.getResultList();
		Set<Priority> prioritysSet = new HashSet<>();
		prioritysSet.addAll(prioritysList);
		s.close();
		return prioritysSet;
	}
	
	// Overloaded methods to get stage data
	public static Stage getById(Stage o, Integer id) {
		Session s = hu.getSession();
		Stage f = s.get(Stage.class, id);
		s.close();
		return f;
	}
	public static Stage getByName(Stage o, String name) {
		return (Stage) hu.getSession()
				.createQuery( "from Stage where name = :name")
				.setParameter("name", name)
				.getSingleResult();
	}
	public static Set<Stage> getAll(Stage o) {
		Session s = hu.getSession();
		String query = "FROM Stage";
		Query<Stage> q = s.createQuery(query, Stage.class);
		List<Stage> stagesList = q.getResultList();
		Set<Stage> stagesSet = new HashSet<>();
		stagesSet.addAll(stagesList);
		s.close();
		return stagesSet;
	}
	
	// Overloaded methods to get decisionType data
	public static DecisionType getById(DecisionType o, Integer id) {
		Session s = hu.getSession();
		DecisionType f = s.get(DecisionType.class, id);
		s.close();
		return f;
	}
	public static DecisionType getByName(DecisionType o, String name) {
		return (DecisionType) hu.getSession()
				.createQuery( "from DecisionType where name = :name")
				.setParameter("name", name)
				.getSingleResult();
	}
	public static Set<DecisionType> getAll(DecisionType o) {
		Session s = hu.getSession();
		String query = "FROM DecisionType";
		Query<DecisionType> q = s.createQuery(query, DecisionType.class);
		List<DecisionType> decisionTypesList = q.getResultList();
		Set<DecisionType> decisionTypesSet = new HashSet<>();
		decisionTypesSet.addAll(decisionTypesList);
		s.close();
		return decisionTypesSet;
	}
}
