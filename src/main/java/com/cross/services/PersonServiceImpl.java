package com.cross.services;

import java.util.Set;

import com.cross.beans.Person;
import com.cross.data.PersonDAO;
import com.cross.data.PersonHibernate;

public class PersonServiceImpl implements PersonService {
	private PersonDAO personDao;
	
	public PersonServiceImpl() {
		personDao = new PersonHibernate();
	}

	@Override
	public Person addPerson(Person p) {
		return personDao.add(p);
	}

	@Override
	public Person getPersonById(Integer id) {
		return personDao.getById(id);
	}

	@Override
	public Person getPersonByUsername(String username) {
		return personDao.getByUsername(username);
	}

	@Override
	public void updatePerson(Person p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deletePerson(Person p) {
		return personDao.delete(p);
		
	}

	@Override
	public Set<Person> getAll() {
		return personDao.getAll(); 
	}

}
