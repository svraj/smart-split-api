package com.triumsys.split.data.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Person extends BaseEntity implements Serializable {

	private String firstName;
	private String lastName;

	@OneToOne
	private Relation relation;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "person_group", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
	private Set<Group> groups = new HashSet<>();

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}

	public Person(String firstName, String lastName, Relation relation) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.relation = relation;
	}

	public Person(Long id, String salutation, String firstName,
			String lastName, Relation relation) {
		super();
		super.setId(id);
		this.firstName = firstName;
		this.lastName = lastName;
		this.relation = relation;
	}

	public Person() {
		// TODO Auto-generated constructor stub
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

}
