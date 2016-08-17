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
import com.triumsys.split.services.SplitService;
import com.triumsys.split.services.business.dto.ParticipantShareDto;
import com.triumsys.split.services.business.dto.PersonBalance;
import com.triumsys.split.services.business.dto.PersonDto;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SplitApplication.class)
@WebAppConfiguration
public class PerPersonBalanceTest {

	@Autowired
	private SplitService splitService;

	@Test
	public void testPerPersonBalances1() throws BusinessException {

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
		List<PersonBalance> personBalances = splitService
				.getPerPersonBalances(participantShares);
		for (PersonBalance personBalance : personBalances) {
			System.out.println(personBalance);
		}

		for (PersonBalance personBalance : personBalances) {

			if (personBalance.getPerson().getId().equals(1l)) {
				Assert.assertEquals("-150.00", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(2l)) {
				Assert.assertEquals("50.00", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(3l)) {
				Assert.assertEquals("0.00", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(4l)) {
				Assert.assertEquals("100.00", personBalance.getAmount()
						.toString());
			}

		}

	}

	@Test
	public void testPerPersonBalances2() throws BusinessException {

		List<ParticipantShareDto> participantShares = new ArrayList<ParticipantShareDto>();
		PersonDto aju = new PersonDto(1l, "Aju", "George");
		PersonDto jk = new PersonDto(2l, "Jayakrishnan", "Balakrishnan");
		PersonDto sujith = new PersonDto(3l, "Sujith", "Nadh");
		PersonDto sajan = new PersonDto(4l, "Sajan", "Raj");

		//@formatter:off
		participantShares.add(new ParticipantShareDto(aju, 1d, BigDecimal.valueOf(10d), BigDecimal.valueOf(40d)));
		participantShares.add(new ParticipantShareDto(jk, 1d, BigDecimal.valueOf(10d), BigDecimal.valueOf(10d)));
		participantShares.add(new ParticipantShareDto(sujith, 2d, BigDecimal.valueOf(20d),	null));
		participantShares.add(new ParticipantShareDto(sajan, 1d, BigDecimal.valueOf(10d),null));
		//@formatter:on
		List<PersonBalance> personBalances = splitService
				.getPerPersonBalances(participantShares);
		for (PersonBalance personBalance : personBalances) {
			System.out.println(personBalance);
		}

		for (PersonBalance personBalance : personBalances) {

			if (personBalance.getPerson().getId().equals(1l)) {
				Assert.assertEquals("-30.00", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(2l)) {
				Assert.assertEquals("0.00", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(3l)) {
				Assert.assertEquals("20.00", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(4l)) {
				Assert.assertEquals("10.00", personBalance.getAmount()
						.toString());
			}

		}

	}

	@Test
	public void testPerPersonBalances3() throws BusinessException {

		List<ParticipantShareDto> participantShares = new ArrayList<ParticipantShareDto>();
		PersonDto aju = new PersonDto(1l, "Aju", "George");
		PersonDto jk = new PersonDto(2l, "Jayakrishnan", "Balakrishnan");
		PersonDto sujith = new PersonDto(3l, "Sujith", "Nadh");
		PersonDto sajan = new PersonDto(4l, "Sajan", "Raj");

		//@formatter:off
		participantShares.add(new ParticipantShareDto(aju, 1d, BigDecimal.valueOf(62.5d), BigDecimal.valueOf(100d)));
		participantShares.add(new ParticipantShareDto(jk, 1d, BigDecimal.valueOf(62.5d), BigDecimal.valueOf(75d)));
		participantShares.add(new ParticipantShareDto(sujith, 1d, BigDecimal.valueOf(62.5d),	BigDecimal.valueOf(75d)));
		participantShares.add(new ParticipantShareDto(sajan, 1d, BigDecimal.valueOf(62.5d),null));
		//@formatter:on
		List<PersonBalance> personBalances = splitService
				.getPerPersonBalances(participantShares);
		for (PersonBalance personBalance : personBalances) {
			System.out.println(personBalance);
		}

		for (PersonBalance personBalance : personBalances) {

			if (personBalance.getPerson().getId().equals(1l)) {
				Assert.assertEquals("-37.50", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(2l)) {
				Assert.assertEquals("-12.50", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(3l)) {
				Assert.assertEquals("-12.50", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(4l)) {
				Assert.assertEquals("62.50", personBalance.getAmount()
						.toString());
			}

		}

	}

	@Test
	public void testPerPersonBalances4() throws BusinessException {

		List<ParticipantShareDto> participantShares = new ArrayList<ParticipantShareDto>();

		PersonDto jk = new PersonDto(1l, "Jayakrishnan", "Balakrishnan");
		PersonDto sujith = new PersonDto(2l, "Sujith", "Nadh");
		PersonDto aju = new PersonDto(3l, "Aju", "George");

		//@formatter:off
		participantShares.add(new ParticipantShareDto(jk, 1.5d, BigDecimal.valueOf(35.33d), BigDecimal.valueOf(98d)));
		participantShares.add(new ParticipantShareDto(sujith, 2d, BigDecimal.valueOf(47.11), BigDecimal.valueOf(8d)));
		participantShares.add(new ParticipantShareDto(aju, 1d, BigDecimal.valueOf(23.56d),	BigDecimal.valueOf(0d)));
		//@formatter:on
		List<PersonBalance> personBalances = splitService
				.getPerPersonBalances(participantShares);
		for (PersonBalance personBalance : personBalances) {
			System.out.println(personBalance);
		}

		for (PersonBalance personBalance : personBalances) {

			if (personBalance.getPerson().getId().equals(1l)) {
				Assert.assertEquals("-62.67", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(2l)) {
				Assert.assertEquals("39.11", personBalance.getAmount()
						.toString());
			}
			if (personBalance.getPerson().getId().equals(3l)) {
				Assert.assertEquals("23.56", personBalance.getAmount()
						.toString());
			}

		}

	}
}
