package com.triumsys.split.data.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("serial")
@Entity(name = "CUSTOM_GROUP")
public class Group extends BaseEntity implements Serializable {

	private String name;
	private String description;

	public Group(String name) {
		this.name = name;
	}

	@JsonIgnore
	@ManyToMany(mappedBy = "groups", fetch = FetchType.EAGER)
	private List<Person> persons;

	public List<Person> getPersons() {
		return persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Group() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
