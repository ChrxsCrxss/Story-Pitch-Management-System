package com.cross.data;

import java.util.Set;

import com.cross.beans.Person;

public interface PersonDAO {

	Person getById(Integer id);

	Set<Person> getAll();

	boolean update(Person t);

	boolean delete(Person t);

	Person add(Person c);

	Person getByUsername(String username);

}
