package com.triumsys.split.controllers;

import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.triumsys.split.data.entity.Group;
import com.triumsys.split.data.entity.Person;
import com.triumsys.split.data.repository.PersonGroupRepository;
import com.triumsys.split.exceptions.BusinessException;
import com.triumsys.split.services.business.SplitBusinessService;
import com.triumsys.split.services.business.dto.ContributionsInfo;

@RestController
@RequestMapping("/groups")
public class GroupController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(GroupController.class);

	@Autowired
	private PersonGroupRepository repository;

	@Autowired
	private SplitBusinessService splitBusinessService;

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<Group>> getGroups() {
		return new ResponseEntity<List<Group>>(repository.findAll(),
				HttpStatus.OK);
	}

	// -------------------Find all Groups----------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Group> getGroup(@PathVariable("id") long id) {
		LOGGER.info("Fetching Group with id " + id);
		Group group = repository.findOne(id);
		if (group == null) {
			LOGGER.info("Group with id " + id + " not found");
			return new ResponseEntity<Group>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Group>(group, HttpStatus.OK);
	}

	// -------------------Find all Persons in Group--------------------
	@RequestMapping(value = "/{id}/persons", method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getPersonsInGroup(
			@PathVariable("id") long id) {
		LOGGER.info("Fetching persons for GroupId-" + id);
		Group group = repository.findOne(id);
		if (group == null) {
			LOGGER.info("Group with id " + id + " not found");
			return new ResponseEntity<List<Person>>(HttpStatus.NOT_FOUND);
		}
		List<Person> persons = group.getPersons();
		return new ResponseEntity<List<Person>>(persons, HttpStatus.OK);
	}

	// -------------------Create Group----------------------------
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createGroup(@RequestBody Group group,
			UriComponentsBuilder ucBuilder) {
		LOGGER.info("Creating Group " + group.getName());
		Long groupId = group.getId();
		if (groupId != null) {
			LOGGER.info("Group with name " + group.getName() + " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		repository.save(group);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/group/{id}")
				.buildAndExpand(group.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update Group-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Group> updateGroup(@PathVariable("id") long id,
			@RequestBody Group group) {
		LOGGER.info("Updating Group " + id);

		Group currentGroup = repository.findOne(id);

		if (currentGroup == null) {
			LOGGER.info("Group with id " + id + " not found");
			return new ResponseEntity<Group>(HttpStatus.NOT_FOUND);
		} else {
			// currentGroup.setName(group.getName());
			// currentGroup.setDescription(group.getDescription());
			Group savedEntity = repository.save(group);
			return new ResponseEntity<Group>(savedEntity, HttpStatus.OK);
		}
	}

	// ------------------- Delete Group-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Group> deleteGroup(@PathVariable("id") long id) {
		LOGGER.info("Fetching & Deleting Group with id " + id);
		Group group = repository.findOne(id);
		if (group == null) {
			LOGGER.info("Unable to delete. Group with id " + id + " not found");
			return new ResponseEntity<Group>(HttpStatus.NOT_FOUND);
		}
		repository.delete(id);
		return new ResponseEntity<Group>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Groups-------------------

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Group> deleteAllGroups() {
		LOGGER.info("Deleting all Groups");
		repository.deleteAll();
		return new ResponseEntity<Group>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}/computeSplits", method = RequestMethod.GET)
	public ResponseEntity<ContributionsInfo> computeDefaultSplits(
			@RequestParam("total") BigDecimal totalAmountBigDecimal,
			@PathVariable("id") long groupId) {
		LOGGER.info("Going to compute default splits for totalAmount - "
				+ totalAmountBigDecimal + " and for groupId-" + groupId);
		ContributionsInfo defaultSplitDto = null;
		try {
			defaultSplitDto = splitBusinessService.computeDefaultSplits(
					groupId, totalAmountBigDecimal);
		} catch (BusinessException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return new ResponseEntity<ContributionsInfo>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ContributionsInfo>(defaultSplitDto,
				HttpStatus.OK);
	}

}
