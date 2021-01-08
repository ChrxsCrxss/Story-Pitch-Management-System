package com.cross.data;

import java.util.Set;

/*
 * This interface represents an abstract database access
 * layer. We should program the controllers to this interface. 
 */
public interface GenericDAO <T> {
	// CRUD operations (create, read, update, delete)
	public T add(T t);
	public T getById(Integer id);
	public Set<T> getAll();
	public boolean update(T t);
	public boolean delete(T t);
}
