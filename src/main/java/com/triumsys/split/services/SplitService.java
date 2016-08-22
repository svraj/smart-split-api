package com.triumsys.split.services;

import java.math.BigDecimal;
import java.util.List;

import com.triumsys.split.data.entity.Person;
import com.triumsys.split.exceptions.BusinessException;
import com.triumsys.split.services.business.dto.ExpenseSplitDto;
import com.triumsys.split.services.business.dto.ParticipantShareDto;
import com.triumsys.split.services.business.dto.ParticipantShareDtoShort;
import com.triumsys.split.services.business.dto.PersonBalance;

public interface SplitService {

	public List<ParticipantShareDto> computeDefaultSplits(
			BigDecimal totalAmount, List<Person> friends)
			throws BusinessException;

	public List<ExpenseSplitDto> computeExpenseSplits(
			List<PersonBalance> calculationInfos) throws BusinessException;

	/*
	 * List<PersonBalance> getPerPersonBalances( List<ParticipantShare>
	 * participantShares, BigDecimal totalAmount) throws BusinessException;
	 */

	public List<PersonBalance> getPerPersonBalances(
			List<ParticipantShareDto> participantShareDtos,
			BigDecimal totalAmount) throws BusinessException;

	public Double getTotalShares(List<ParticipantShareDto> participantShareDtos);

	public List<PersonBalance> getPerPersonBalances(
			List<ParticipantShareDto> participantShares)
			throws BusinessException;

	public Double getTotalSharesForShort(
			List<ParticipantShareDtoShort> participantShares);

}
