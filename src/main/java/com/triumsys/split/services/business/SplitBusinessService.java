package com.triumsys.split.services.business;

import java.math.BigDecimal;
import java.util.List;

import com.triumsys.split.exceptions.BusinessException;
import com.triumsys.split.services.business.dto.ContributionsInfo;
import com.triumsys.split.services.business.dto.ParticipantShareDto;
import com.triumsys.split.services.business.dto.SplitCalculationWithBalances;

public interface SplitBusinessService {

	public ContributionsInfo computeDefaultSplits(Long groupId,
			BigDecimal totalAmountBigDecimal) throws BusinessException;

	public SplitCalculationWithBalances doSplitCalculation(
			ContributionsInfo contributionsInfo) throws BusinessException;

	public ContributionsInfo adjustShareAmounts(
			ContributionsInfo contributionsInfo) throws BusinessException;

	public List<ParticipantShareDto> calculatePerShareAmount(
			List<ParticipantShareDto> particpantShares)
			throws BusinessException;

	public SplitCalculationWithBalances smartSplit(
			List<ParticipantShareDto> participantShares)
			throws BusinessException;

}
