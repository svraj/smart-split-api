package com.triumsys.split.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.triumsys.split.exceptions.BusinessException;
import com.triumsys.split.services.business.SplitBusinessService;
import com.triumsys.split.services.business.dto.ParticipantShareDto;
import com.triumsys.split.services.business.dto.ParticipantShareDtoShort;
import com.triumsys.split.services.business.dto.SplitCalculationWithBalances;

@RestController
@RequestMapping("/expense-split")
public class ExpenseSplitController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ExpenseSplitController.class);

	@Autowired
	private SplitBusinessService splitBusinessService;

	@RequestMapping(value = "/calculatePerShareAmountShort", method = RequestMethod.POST)
	public ResponseEntity<List<ParticipantShareDtoShort>> calculatePerShareAmountShort(
			@RequestBody List<ParticipantShareDtoShort> participantShares) {
		LOGGER.info("Going to adjust ShareAmt for - " + participantShares);
		List<ParticipantShareDtoShort> newParticipantShares = null;
		try {
			newParticipantShares = splitBusinessService
					.calculatePerShareAmountShort(participantShares);
		} catch (BusinessException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return new ResponseEntity<List<ParticipantShareDtoShort>>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ParticipantShareDtoShort>>(
				newParticipantShares, HttpStatus.OK);
	}

	@RequestMapping(value = "/calculatePerShareAmount", method = RequestMethod.POST)
	public ResponseEntity<List<ParticipantShareDto>> calculatePerShareAmount(
			@RequestBody List<ParticipantShareDto> participantShares) {
		LOGGER.info("Going to adjust ShareAmt for - " + participantShares);
		List<ParticipantShareDto> newParticipantShares = null;
		try {
			newParticipantShares = splitBusinessService
					.calculatePerShareAmount(participantShares);
		} catch (BusinessException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return new ResponseEntity<List<ParticipantShareDto>>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<ParticipantShareDto>>(
				newParticipantShares, HttpStatus.OK);
	}

	@RequestMapping(value = "/smart-split", method = RequestMethod.POST)
	public ResponseEntity<SplitCalculationWithBalances> smartSplit(
			@RequestBody List<ParticipantShareDto> participantShares) {
		LOGGER.info("Going to do split calculation for participantShares : "
				+ participantShares);

		SplitCalculationWithBalances splitCalculationWithBalances = null;
		try {
			splitCalculationWithBalances = splitBusinessService
					.smartSplit(participantShares);
			LOGGER.info("SplitCalculationWithBalances-"
					+ splitCalculationWithBalances);
		} catch (BusinessException e) {
			e.printStackTrace();
			LOGGER.error(e.getMessage());
			return new ResponseEntity<SplitCalculationWithBalances>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<SplitCalculationWithBalances>(
				splitCalculationWithBalances, HttpStatus.OK);

	}

}
