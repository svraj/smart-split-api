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

import com.triumsys.split.data.entity.Expense;
import com.triumsys.split.data.repository.ExpenseRepository;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExpenseController.class);

	@Autowired
	private ExpenseRepository repository;

	// -------------------Find all Expenses----------------------------
	@RequestMapping(method = RequestMethod.GET)
	public HttpEntity<List<Expense>> getExpenses() {
		return new ResponseEntity<List<Expense>>(repository.findAll(),
				HttpStatus.OK);
	}

	// -------------------Find Expense----------------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Expense> getExpense(@PathVariable("id") long id) {
		LOGGER.info("Fetching Expense with id " + id);
		Expense expense = repository.findOne(id);
		if (expense == null) {
			LOGGER.info("Expense with id " + id + " not found");
			return new ResponseEntity<Expense>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Expense>(expense, HttpStatus.OK);
	}

	// -------------------Create Expense----------------------------
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> createExpense(@RequestBody Expense expense,
			UriComponentsBuilder ucBuilder) {
		LOGGER.info("Creating Expense " + expense.toString());

		/*
		 * if (expense.getParticipantShares() != null) { for (ParticipantShare
		 * share : expense.getParticipantShares()) { share.setExpense(expense);
		 * } }
		 */
		LOGGER.info("Shares-" + expense.getParticipantShares());
		Long expenseId = expense.getId();
		if (expenseId != null) {
			LOGGER.info("Expense with name " + expense.getName()
					+ " already exist");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		repository.save(expense);
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/expense/{id}")
				.buildAndExpand(expense.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ------------------- Update Expense-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Expense> updateExpense(@PathVariable("id") long id,
			@RequestBody Expense expense) {
		LOGGER.info("Updating Expense " + id);

		Expense currentExpense = repository.findOne(id);

		if (currentExpense == null) {
			LOGGER.info("Expense with id " + id + " not found");
			return new ResponseEntity<Expense>(HttpStatus.NOT_FOUND);
		} else {
			/*
			 * if (expense.getParticipantShares() != null) { for
			 * (ParticipantShare share : expense.getParticipantShares()) {
			 * share.setExpense(expense); } }
			 */
			// currentExpense.setParticipantShares(expense.getParticipantShares());
			Expense savedEntity = repository.save(expense);

			LOGGER.info("Shares-" + expense.getParticipantShares());
			return new ResponseEntity<Expense>(savedEntity, HttpStatus.OK);
		}
	}

	// ------------------- Delete Expense-------------------
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Expense> deleteExpense(@PathVariable("id") long id) {
		LOGGER.info("Fetching & Deleting Expense with id " + id);
		Expense expense = repository.findOne(id);
		if (expense == null) {
			LOGGER.info("Unable to delete. Expense with id " + id
					+ " not found");
			return new ResponseEntity<Expense>(HttpStatus.NOT_FOUND);
		}
		repository.delete(id);
		return new ResponseEntity<Expense>(HttpStatus.NO_CONTENT);
	}

	// ------------------- Delete All Expenses-------------------

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<Expense> deleteAllExpenses() {
		LOGGER.info("Deleting All Expenses");
		repository.deleteAll();
		return new ResponseEntity<Expense>(HttpStatus.NO_CONTENT);
	}

}
