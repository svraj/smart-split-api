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

import com.triumsys.split.data.entity.ExpenseCategory;
import com.triumsys.split.data.repository.ExpenseCategoryRepository;

@RestController
@RequestMapping("/expense-categories")
public class ExpenseCategoryController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExpenseCategoryController.class);

	@Autowired
	private ExpenseCategoryRepository repository;

	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<ExpenseCategory>> getExpenseCategories() {
		return new ResponseEntity<List<ExpenseCategory>>(repository.findAll(),
				HttpStatus.OK);
	}

	// -------------------Find all ExpenseCategories----------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ExpenseCategory> getExpenseCategory(
			@PathVariable("id") long id) {
		LOGGER.info("Fetching ExpenseCategory with id " + id);
		ExpenseCategory relation = repository.findOne(id);
		if (relation == null) {
			LOGGER.info("ExpenseCategory with id " + id + " not found");
			return new ResponseEntity<ExpenseCategory>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ExpenseCategory>(relation, HttpStatus.OK);
	}

	// -------------------Create a ExpenseCategory----------------------------
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createExpenseCategory(
			@RequestBody ExpenseCategory relation,
			UriComponentsBuilder ucBuilder) {
		LOGGER.info("Creating ExpenseCategory " + relation.getName());
		Long relationId = relation.getId();
		if (relationId != null) {
			LOGGER.info("ExpenseCategory with name " + relation.getName()
					+ " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		repository.save(relation);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/relation/{id}")
				.buildAndExpand(relation.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update a ExpenseCategory-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ExpenseCategory> updateExpenseCategory(
			@PathVariable("id") long id,
			@RequestBody ExpenseCategory expenseCategory) {
		LOGGER.info("Updating ExpenseCategory " + id);

		ExpenseCategory currentExpenseCategory = repository.findOne(id);

		if (currentExpenseCategory == null) {
			LOGGER.info("ExpenseCategory with id " + id + " not found");
			return new ResponseEntity<ExpenseCategory>(HttpStatus.NOT_FOUND);
		} else {
			// currentExpenseCategory.setName(expenseCategory.getName());
			// currentExpenseCategory.setDescription(expenseCategory.getDescription());
			ExpenseCategory savedEntity = repository.save(expenseCategory);
			return new ResponseEntity<ExpenseCategory>(savedEntity,
					HttpStatus.OK);
		}

	}

	// ------------------- Delete a ExpenseCategory-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ExpenseCategory> deleteExpenseCategory(
			@PathVariable("id") long id) {
		LOGGER.info("Fetching & Deleting ExpenseCategory with id " + id);
		ExpenseCategory relation = repository.findOne(id);
		if (relation == null) {
			LOGGER.info("Unable to delete. ExpenseCategory with id " + id
					+ " not found");
			return new ResponseEntity<ExpenseCategory>(HttpStatus.NOT_FOUND);
		}
		repository.delete(id);
		return new ResponseEntity<ExpenseCategory>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All ExpenseCategories-------------------

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<ExpenseCategory> deleteAllExpenseCategorys() {
		LOGGER.info("Deleting all ExpenseCategories");
		repository.deleteAll();
		return new ResponseEntity<ExpenseCategory>(HttpStatus.NO_CONTENT);
	}

}
