package com.triumsys.split.services.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.triumsys.split.data.entity.ParticipantShare;
import com.triumsys.split.data.repository.PersonGroupRepository;
import com.triumsys.split.exceptions.BusinessException;
import com.triumsys.split.services.SplitService;
import com.triumsys.split.services.business.dto.ExpenseSplitDto;
import com.triumsys.split.services.business.dto.ParticipantShareDto;
import com.triumsys.split.services.business.dto.ParticipantShareDtoShort;
import com.triumsys.split.services.business.dto.PersonBalance;
import com.triumsys.split.services.business.dto.SplitCalculationWithBalances;

@Service("splitBusinessService")
public class SplitBusinessServiceImpl implements SplitBusinessService {

	@Autowired
	private SplitService splitService;

	@Autowired
	private PersonGroupRepository personGroupRepository;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SplitBusinessServiceImpl.class);

	@Override
	public SplitCalculationWithBalances smartSplit(
			List<ParticipantShareDto> participantShares)
			throws BusinessException {
		SplitCalculationWithBalances calculationWithBalances = null;
		if (!CollectionUtils.isEmpty(participantShares)) {
			LOGGER.info("Going to smart split the below partipantShares....");
			for (ParticipantShareDto participantShare : participantShares) {
				LOGGER.info(participantShare.toString());
			}
			calculationWithBalances = new SplitCalculationWithBalances();
			List<PersonBalance> perPersonBalances = splitService
					.getPerPersonBalances(participantShares);
			LOGGER.info("PersonBalances-" + perPersonBalances);
			if (perPersonBalances != null) {
				List<ExpenseSplitDto> expenseSplitDTOs = splitService
						.computeExpenseSplits(perPersonBalances);
				calculationWithBalances.setExpenseSplitDtos(expenseSplitDTOs);
				calculationWithBalances.setPersonBalances(perPersonBalances);
				calculationWithBalances.setParticipantShares(participantShares);
			} else {
				LOGGER.error("Per person balances returned as null!!");
			}
			LOGGER.info("CalculationWithBalances-" + calculationWithBalances);
		} else {
			LOGGER.info("Participant Shares must not be empty");
			throw new BusinessException("Participant Shares must not be empty!");
		}
		return calculationWithBalances;
	}

	private ParticipantShare convertFromDTO(
			ParticipantShareDto participantShareDTO) {
		ParticipantShare participantShare = null;

		return participantShare;
	}

	@Override
	public List<ParticipantShareDto> calculatePerShareAmount(
			List<ParticipantShareDto> participantShares)
			throws BusinessException {
		List<ParticipantShareDto> newParticipantShares = null;
		if (participantShares != null) {
			newParticipantShares = new ArrayList<ParticipantShareDto>();
			BigDecimal newTotal = computeTotal(participantShares);
			LOGGER.info("new Total is -" + newTotal);
			BigDecimal singleShareAmt = BigDecimal.ZERO;
			Double totalShares = splitService.getTotalShares(participantShares);
			if (totalShares != 0) {
				singleShareAmt = newTotal
						.divide(new BigDecimal(totalShares), 2);
			}
			for (ParticipantShareDto participantShareDto : participantShares) {
				ParticipantShareDto newParticipantShare = new ParticipantShareDto();
				newParticipantShare.setPerson(participantShareDto.getPerson());
				newParticipantShare
						.setPaidAmt(participantShareDto.getPaidAmt());
				newParticipantShare
						.setRemarks(participantShareDto.getRemarks());
				newParticipantShare.setShare(participantShareDto.getShare());
				newParticipantShare
						.setShareAmt(singleShareAmt.multiply(new BigDecimal(
								participantShareDto.getShare())));
				newParticipantShares.add(newParticipantShare);
			}
		}
		return newParticipantShares;
	}

	@Override
	public List<ParticipantShareDtoShort> calculatePerShareAmountShort(
			List<ParticipantShareDtoShort> participantShares) {
		List<ParticipantShareDtoShort> newParticipantShares = null;
		if (participantShares != null) {
			newParticipantShares = new ArrayList<ParticipantShareDtoShort>();
			BigDecimal newTotal = computeTotalForShort(participantShares);
			LOGGER.info("new Total is -" + newTotal);
			BigDecimal singleShareAmt = BigDecimal.ZERO;
			Double totalShares = splitService
					.getTotalSharesForShort(participantShares);
			if (totalShares != 0) {
				singleShareAmt = newTotal
						.divide(new BigDecimal(totalShares), 2);
			}
			for (ParticipantShareDtoShort participantShareDto : participantShares) {
				ParticipantShareDtoShort newParticipantShare = new ParticipantShareDtoShort();
				newParticipantShare.setPerson(participantShareDto.getPerson());
				newParticipantShare
						.setPaidAmt(participantShareDto.getPaidAmt());
				newParticipantShare
						.setRemarks(participantShareDto.getRemarks());
				newParticipantShare.setShare(participantShareDto.getShare());
				newParticipantShare
						.setShareAmt(singleShareAmt.multiply(new BigDecimal(
								participantShareDto.getShare())));
				newParticipantShares.add(newParticipantShare);
			}
		}
		return newParticipantShares;
	}

	private BigDecimal computeTotal(List<ParticipantShareDto> participantShares) {
		BigDecimal total = BigDecimal.ZERO;
		for (ParticipantShareDto participantShare : participantShares) {
			if (participantShare.getPaidAmt() != null) {
				total = total.add(participantShare.getPaidAmt());
			}
		}
		return total;
	}

	private BigDecimal computeTotalForShort(
			List<ParticipantShareDtoShort> participantShares) {
		BigDecimal total = BigDecimal.ZERO;
		for (ParticipantShareDtoShort participantShare : participantShares) {
			if (participantShare.getPaidAmt() != null) {
				total = total.add(participantShare.getPaidAmt());
			}
		}
		return total;
	}

}
