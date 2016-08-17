package com.triumsys.split.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.triumsys.split.data.entity.ParticipantShare;
import com.triumsys.split.data.entity.Person;
import com.triumsys.split.exceptions.BusinessException;
import com.triumsys.split.service.comparators.CalculationObjComparator;
import com.triumsys.split.services.business.dto.CalculationObj;
import com.triumsys.split.services.business.dto.ExpenseSplitDto;
import com.triumsys.split.services.business.dto.ParticipantShareDto;
import com.triumsys.split.services.business.dto.PersonBalance;
import com.triumsys.split.services.business.dto.PersonDto;
import com.triumsys.split.services.constant.ServiceConstants;

@Service("splitService")
public class SplitServiceImpl implements SplitService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SplitServiceImpl.class);

	@Override
	public List<ParticipantShareDto> computeDefaultSplits(
			BigDecimal totalAmount, List<Person> friends)
			throws BusinessException {
		List<ParticipantShareDto> defaultSplits = null;

		if (friends != null && friends.size() > 0) {
			int teamSize = friends.size();
			BigDecimal perShareAmount = null;
			if (totalAmount != null
					&& totalAmount.compareTo(BigDecimal.ZERO) > 0) {
				perShareAmount = totalAmount.divide(
						BigDecimal.valueOf(teamSize), 2, 2);
				/*
				 * perShareAmount = perShareAmount.setScale(2,
				 * RoundingMode.CEILING);
				 */
			}
			defaultSplits = new ArrayList<ParticipantShareDto>();
			for (int index = 0; index < teamSize; index++) {
				ParticipantShare perShare = new ParticipantShare(
						friends.get(index), 1d, perShareAmount);
				ParticipantShareDto defaultSplit = convertParticipantShareToDefaultSplit(perShare);
				defaultSplits.add(defaultSplit);
			}
		}

		return defaultSplits;
	}

	private ParticipantShareDto convertParticipantShareToDefaultSplit(
			ParticipantShare participantShare) {
		ParticipantShareDto defautlSplit = null;
		if (participantShare != null && participantShare.getPerson() != null) {
			defautlSplit = new ParticipantShareDto();
			PersonDto personDto = new PersonDto();
			personDto.setId(participantShare.getPerson().getId());
			personDto.setFirstName(participantShare.getPerson().getFirstName());
			personDto.setLastName(participantShare.getPerson().getLastName());
			defautlSplit.setPerson(personDto);
			defautlSplit.setShare(participantShare.getShare());
			defautlSplit.setPaidAmt(participantShare.getPaidAmt());
			defautlSplit.setShareAmt(participantShare.getShareAmt());
			defautlSplit.setRemarks(participantShare.getRemarks());

		}
		return defautlSplit;
	}

	@Override
	public List<PersonBalance> getPerPersonBalances(
			List<ParticipantShareDto> participantShareDtos,
			BigDecimal totalAmount) throws BusinessException {
		List<PersonBalance> calculationInfos = null;
		if (participantShareDtos != null && participantShareDtos.size() > 0) {
			calculationInfos = new ArrayList<PersonBalance>();
			Double totalShares = getTotalShares(participantShareDtos);
			for (ParticipantShareDto participantShareDto : participantShareDtos) {
				PersonBalance calculationInfo = computePerPersonBalance(
						totalAmount, participantShareDto, totalShares);
				if (calculationInfo != null) {
					calculationInfos.add(calculationInfo);
				}
			}
		} else {
			throw new BusinessException(ServiceConstants.PARTICIPANTS_NULL);
		}
		return calculationInfos;
	}

	@Override
	public List<PersonBalance> getPerPersonBalances(
			List<ParticipantShareDto> participantShares)
			throws BusinessException {
		List<PersonBalance> personBalancesList = null;
		Map<Long, PersonBalance> personBalancesMap = null;
		if (participantShares != null && participantShares.size() > 0) {
			personBalancesMap = new HashMap<Long, PersonBalance>();
			for (ParticipantShareDto participantShareDto : participantShares) {
				computePerPersonBalance(participantShareDto, personBalancesMap);
			}
		} else {
			throw new BusinessException(ServiceConstants.PARTICIPANTS_NULL);
		}

		if (personBalancesMap != null) {
			personBalancesList = new ArrayList<PersonBalance>();
			for (Long personId : personBalancesMap.keySet()) {
				personBalancesList.add(personBalancesMap.get(personId));
			}
		}
		return personBalancesList;
	}

	private void computePerPersonBalance(
			ParticipantShareDto participantShareDto,
			Map<Long, PersonBalance> personBalancesMap) {

		Long personId = participantShareDto.getPerson().getId();
		PersonBalance personBalance = personBalancesMap.get(personId);
		BigDecimal paidInThisShare = participantShareDto.getPaidAmt();
		BigDecimal spentInThisShare = participantShareDto.getShareAmt();

		if (personBalance != null) {
			if (personBalance.getTotalPaid() == null) {
				personBalance.setTotalPaid(BigDecimal.ZERO);
			}

			if (personBalance.getTotalSpent() == null) {
				personBalance.setTotalSpent(BigDecimal.ZERO);
			}

			if (paidInThisShare != null) {
				personBalance.setTotalPaid(personBalance.getTotalPaid().add(
						paidInThisShare));
			}
			if (spentInThisShare != null) {
				personBalance.setTotalSpent(personBalance.getTotalSpent().add(
						spentInThisShare));
			}
		} else {
			PersonBalance newPersonBalance = new PersonBalance();
			newPersonBalance.setPerson(participantShareDto.getPerson());
			newPersonBalance
					.setTotalPaid(paidInThisShare == null ? BigDecimal.ZERO
							: paidInThisShare);
			newPersonBalance
					.setTotalSpent(spentInThisShare == null ? BigDecimal.ZERO
							: spentInThisShare);
			personBalancesMap.put(newPersonBalance.getPerson().getId(),
					newPersonBalance);
		}
	}

	private PersonBalance computePerPersonBalance(
			BigDecimal totalExpenseAmount,
			ParticipantShareDto participantShareDto, Double totalShares)
			throws BusinessException {
		PersonBalance calculationInfo = null;
		if (totalExpenseAmount == null || totalExpenseAmount == BigDecimal.ZERO) {
			throw new BusinessException(ServiceConstants.EXPENSE_TOTAL_NULL);
		}
		if (participantShareDto != null && totalExpenseAmount != null
				& !totalExpenseAmount.equals(BigDecimal.ZERO)) {
			calculationInfo = new PersonBalance();
			BigDecimal perShareAmount = BigDecimal.ZERO;
			if (totalShares != 0) {
				perShareAmount = totalExpenseAmount.divide(new BigDecimal(
						totalShares), 2);
				perShareAmount = perShareAmount.multiply(new BigDecimal(
						participantShareDto.getShare()));
				if (isExpensePaidByParticipant(participantShareDto)) {
					perShareAmount = perShareAmount
							.subtract(participantShareDto.getPaidAmt());
				}
				calculationInfo.setAmount(perShareAmount);
			}
			calculationInfo.setPerson(participantShareDto.getPerson());
		}
		return calculationInfo;
	}

	private boolean isExpensePaidByParticipant(
			ParticipantShareDto participantShareDto) {
		if (participantShareDto != null
				&& participantShareDto.getPaidAmt() != null
				&& participantShareDto.getPerson() != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Double getTotalShares(List<ParticipantShareDto> participantShareDtos) {
		Double totalShares = 0d;
		if (participantShareDtos != null) {
			for (ParticipantShareDto participantShareDto : participantShareDtos) {
				if (participantShareDto.getShare() != null) {
					totalShares += participantShareDto.getShare();
				}
			}
		}
		return totalShares;
	}

	@Override
	public List<ExpenseSplitDto> computeExpenseSplits(
			List<PersonBalance> perPersonBalances) throws BusinessException {

		if (perPersonBalances == null || perPersonBalances.size() == 0) {
			throw new BusinessException("Shares per person is empty");
		}
		List<CalculationObj> calculationObjects = new ArrayList<CalculationObj>();
		// personBalancesForCalculation = perPersonBalances;
		for (PersonBalance temp : perPersonBalances) {
			calculationObjects.add(new CalculationObj(temp.getPerson(), temp
					.getAmount()));
		}
		List<ExpenseSplitDto> transactionSplits = null;
		if (calculationObjects != null) {
			transactionSplits = new ArrayList<ExpenseSplitDto>();

			while (calculationObjects.size() > 0) {
				Collections.sort(calculationObjects,
						new CalculationObjComparator());
				CalculationObj richest = calculationObjects.get(0);

				CalculationObj poorest = calculationObjects
						.get(calculationObjects.size() - 1);
				ExpenseSplitDto transaction = transact(richest, poorest,
						calculationObjects);
				if (transaction != null) {
					transactionSplits.add(transaction);
				}
			}
		}
		return transactionSplits;
	}

	private ExpenseSplitDto transact(CalculationObj richest,
			CalculationObj poorest, List<CalculationObj> eligibleCalculations) {
		ExpenseSplitDto transaction = null;
		// if (!(poorest.getPerson().equals(richest.getPerson()))) {
		LOGGER.info("Eligible Calculations before transact: "
				+ eligibleCalculations);
		if (poorest.getBalance().compareTo(richest.getBalance()) > 0) {
			LOGGER.info("poorest" + poorest);
			LOGGER.info("richest" + richest);
			transaction = new ExpenseSplitDto();
			// transaction.setExpense(poorest.getExpense());

			transaction.setFrom(poorest.getPerson());
			transaction.setTo(richest.getPerson());
			transaction.setRemarks("Computed Transaction");
			BigDecimal transferAmount = BigDecimal.ZERO;
			BigDecimal newBalanceAmount = BigDecimal.ZERO;
			boolean removeRichest = false;
			boolean removePoorest = false;

			if (poorest.getBalance().abs()
					.compareTo(richest.getBalance().abs()) > 0) {
				transferAmount = richest.getBalance().abs();
				newBalanceAmount = poorest.getBalance()
						.subtract(transferAmount);
				LOGGER.info("New Balance Amt : " + newBalanceAmount);
				removeRichest = true;
			} else {
				if (poorest.getBalance().abs()
						.compareTo(richest.getBalance().abs()) < 0) {
					transferAmount = poorest.getBalance().abs();
					newBalanceAmount = richest.getBalance().add(transferAmount);
					removePoorest = true;
					removeRichest = true;
				} else {
					// Both are equal
					transferAmount = poorest.getBalance().abs();
					removeRichest = true;
					removePoorest = true;
				}
			}

			transaction.setAmount(transferAmount);

			CalculationObj newPersonBalance = null;
			if (removeRichest) {
				eligibleCalculations.remove(richest);
				LOGGER.info("Removed richest-" + richest);
				newPersonBalance = poorest;
				newPersonBalance.setBalance(newBalanceAmount);
				LOGGER.info("New person Balance-" + newPersonBalance);
			}

			if (removePoorest) {
				eligibleCalculations.remove(poorest);
				LOGGER.info("Removed poorest-" + poorest);
				newPersonBalance = richest;
				newPersonBalance.setBalance(newBalanceAmount);
				LOGGER.info("New person Balance-" + newPersonBalance);
			}

			if (newPersonBalance != null) {
				eligibleCalculations.add(newPersonBalance);
				LOGGER.info("Added to EligibleCalculations-" + newPersonBalance);
			}

		} else {
			if (poorest.getBalance().compareTo(richest.getBalance()) == 0) {
				eligibleCalculations.remove(richest);
				eligibleCalculations.remove(poorest);
			}
			// }

		}
		LOGGER.info("Eligible Calculations after transact : "
				+ eligibleCalculations);
		LOGGER.info("Transacted");

		return transaction;
	}

}
