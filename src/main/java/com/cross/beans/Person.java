package com.cross.beans;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Person {
	@Id
	private Integer id; 
	private String username; 
	@Column(name="passwd")
	private String password; 
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="role_id")
	private Role role;
	private Integer points; 
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="editor_genre",
			joinColumns=@JoinColumn(name="editor_id"),
			inverseJoinColumns=@JoinColumn(name="genre_id"))
	private Set<Genre> genres; 
	
	public Person() {}

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public Integer getPoints() {
		return points;
	}


	public void setPoints(Integer points) {
		this.points = points;
	}
	
	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		

		return String.format(
				  "id: %d\n"
				+ "username: %s\n"
				+ "password: *******\n"
				+ "role(s): %s\n",
				id, username, role.getName()
				);
	}

}
