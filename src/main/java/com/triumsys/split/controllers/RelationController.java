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

import com.triumsys.split.data.entity.Relation;
import com.triumsys.split.data.repository.RelationRepository;

@RestController
@RequestMapping("/relations")
public class RelationController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(RelationController.class);

	@Autowired
	private RelationRepository repository;

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<Relation>> getRelations() {
		return new ResponseEntity<List<Relation>>(repository.findAll(),
				HttpStatus.OK);
	}

	// -------------------Find all Relations----------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Relation> getRelation(@PathVariable("id") long id) {
		LOGGER.info("Fetching Relation with id " + id);
		Relation relation = repository.findOne(id);
		if (relation == null) {
			LOGGER.info("Relation with id " + id + " not found");
			return new ResponseEntity<Relation>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Relation>(relation, HttpStatus.OK);
	}

	// -------------------Create a Relation----------------------------
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createRelation(@RequestBody Relation relation,
			UriComponentsBuilder ucBuilder) {
		LOGGER.info("Creating Relation " + relation.getName());
		Long relationId = relation.getId();
		if (relationId != null) {
			LOGGER.info("Relation with name " + relation.getName()
					+ " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		repository.save(relation);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/relation/{id}")
				.buildAndExpand(relation.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a Relation-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Relation> updateRelation(@PathVariable("id") long id,
			@RequestBody Relation relation) {
		LOGGER.info("Updating Relation " + id);

		Relation currentRelation = repository.findOne(id);

		if (currentRelation == null) {
			LOGGER.info("Relation with id " + id + " not found");
			return new ResponseEntity<Relation>(HttpStatus.NOT_FOUND);
		} else {
			// currentRelation.setName(relation.getName());
			// currentRelation.setDescription(relation.getDescription());
			Relation savedEntity = repository.save(relation);
			return new ResponseEntity<Relation>(savedEntity, HttpStatus.OK);
		}
	}

	// ------------------- Delete a Relation-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Relation> deleteRelation(@PathVariable("id") long id) {
		LOGGER.info("Fetching & Deleting Relation with id " + id);
		Relation relation = repository.findOne(id);
		if (relation == null) {
			LOGGER.info("Unable to delete. Relation with id " + id
					+ " not found");
			return new ResponseEntity<Relation>(HttpStatus.NOT_FOUND);
		}
		repository.delete(id);
		return new ResponseEntity<Relation>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Relations-------------------

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Relation> deleteAllRelations() {
		LOGGER.info("Deleting all Relations");
		repository.deleteAll();
		return new ResponseEntity<Relation>(HttpStatus.NO_CONTENT);
	}

}
