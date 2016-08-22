package com.triumsys.split.services.business;

import java.util.List;

import com.triumsys.split.exceptions.BusinessException;
import com.triumsys.split.services.business.dto.ParticipantShareDto;
import com.triumsys.split.services.business.dto.ParticipantShareDtoShort;
import com.triumsys.split.services.business.dto.SplitCalculationWithBalances;

public interface SplitBusinessService {

	public List<ParticipantShareDto> calculatePerShareAmount(
			List<ParticipantShareDto> particpantShares)
			throws BusinessException;

	public SplitCalculationWithBalances smartSplit(
			List<ParticipantShareDto> participantShares)
			throws BusinessException;

	public List<ParticipantShareDtoShort> calculatePerShareAmountShort(
			List<ParticipantShareDtoShort> participantShares)
			throws BusinessException;

}
