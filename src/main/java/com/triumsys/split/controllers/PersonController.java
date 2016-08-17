package com.triumsys.split.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.triumsys.split.data.entity.Person;
import com.triumsys.split.data.repository.PersonRepository;

@RestController
@RequestMapping("/persons")
public class PersonController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PersonController.class);

	@Autowired
	private PersonRepository repository;

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<Person>> getPersons() {
		return new ResponseEntity<List<Person>>(repository.findAll(),
				HttpStatus.OK);
	}

	// -------------------Find all Persons----------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
		LOGGER.info("Fetching Person with id " + id);
		Person person = repository.findOne(id);
		if (person == null) {
			LOGGER.info("Person with id " + id + " not found");
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Person>(person, HttpStatus.OK);
	}

	// -------------------Create a Person----------------------------
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createPerson(@RequestBody Person person,
			UriComponentsBuilder ucBuilder) {
		LOGGER.info("Creating Person with first Name -" + person.getFirstName()
				+ " and lastName-" + person.getLastName());
		Long personId = person.getId();
		if (personId != null) {
			LOGGER.info("Person with with first Name -" + person.getFirstName()
					+ " and lastName-" + person.getLastName()
					+ " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		repository.save(person);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/person/{id}")
				.buildAndExpand(person.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Person-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Person> updatePerson(@PathVariable("id") long id,
			@RequestBody Person person) {
		LOGGER.info("Updating Person " + id);

		Person currentPerson = repository.findOne(id);

		if (currentPerson == null) {
			LOGGER.info("Person with id " + id + " not found");
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		} else {
			Person savedEntity = repository.save(person);
			return new ResponseEntity<Person>(savedEntity, HttpStatus.OK);
		}
	}

	// ------------------- Delete a Person-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Person> deletePerson(@PathVariable("id") long id) {
		LOGGER.info("Fetching & Deleting Person with id " + id);
		Person person = repository.findOne(id);
		if (person == null) {
			LOGGER.info("Unable to delete. Person with id " + id + " not found");
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		repository.delete(id);
		return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Persons-------------------

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Person> deleteAllPersons() {
		LOGGER.info("Deleting All Persons");
		repository.deleteAll();
		return new ResponseEntity<Person>(HttpStatus.NO_CONTENT);
	}

}
