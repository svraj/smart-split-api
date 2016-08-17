package com.triumsys.split.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.triumsys.split.SplitApplication;
import com.triumsys.split.exceptions.BusinessException;
import com.triumsys.split.services.business.SplitBusinessService;
import com.triumsys.split.services.business.dto.ExpenseSplitDto;
import com.triumsys.split.services.business.dto.ParticipantShareDto;
import com.triumsys.split.services.business.dto.PersonBalance;
import com.triumsys.split.services.business.dto.PersonDto;
import com.triumsys.split.services.business.dto.SplitCalculationWithBalances;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SplitApplication.class)
@WebAppConfiguration
public class ExpenseSplitTest {

	@Autowired
	private SplitBusinessService splitBusinessService;

	@Test
	public void testExpenseSplits() throws BusinessException {
		List<ParticipantShareDto> participantShares = new ArrayList<ParticipantShareDto>();
		PersonDto aju = new PersonDto(1l, "Aju", "George");
		PersonDto jk = new PersonDto(2l, "Jayakrishnan", "Balakrishnan");
		PersonDto sujith = new PersonDto(3l, "Sujith", "Nadh");
		PersonDto sajan = new PersonDto(4l, "Sajan", "Raj");

		//@formatter:off
		participantShares.add(new ParticipantShareDto(aju, 2d, BigDecimal.valueOf(100d), BigDecimal.valueOf(250d)));
		participantShares.add(new ParticipantShareDto(jk, 1d, BigDecimal.valueOf(50d), null));
		participantShares.add(new ParticipantShareDto(sujith, 0d, null,	null));
		participantShares.add(new ParticipantShareDto(sajan, 2d, BigDecimal.valueOf(100d),null));
		//@formatter:on

		SplitCalculationWithBalances splitCalculationWithBalances = null;
		splitCalculationWithBalances = splitBusinessService
				.smartSplit(participantShares);
		// System.out.println(splitCalculationWithBalances);
		for (PersonBalance personBalance : splitCalculationWithBalances
				.getPersonBalances()) {
			System.out.println(personBalance);
		}
		for (ExpenseSplitDto expenseSplit : splitCalculationWithBalances
				.getExpenseSplitDtos()) {
			System.out.println(expenseSplit);
		}
		Assert.assertNotNull(splitCalculationWithBalances);
		Assert.assertNotNull(splitCalculationWithBalances.getExpenseSplitDtos());
		Assert.assertNotNull(splitCalculationWithBalances.getPersonBalances());
		Assert.assertTrue(splitCalculationWithBalances.getPersonBalances()
				.size() > 0);
		Assert.assertTrue(splitCalculationWithBalances.getExpenseSplitDtos()
				.size() > 0);

	}

}
