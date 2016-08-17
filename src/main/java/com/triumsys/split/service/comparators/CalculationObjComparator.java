package com.triumsys.split.service.comparators;

import java.util.Comparator;

import com.triumsys.split.services.business.dto.CalculationObj;

public class CalculationObjComparator implements Comparator<CalculationObj> {

	@Override
	public int compare(CalculationObj o1, CalculationObj o2) {
		if (o1 == null && o2 == null) {
			return 0;
		}

		if (o1 != null && o2 == null) {
			return 1;
		}

		if (o1 == null && o2 != null) {
			return -1;
		}

		if (o1 != null && o2 != null) {

			if (o1.getBalance() == null && o2.getBalance() == null) {
				return 0;
			}

			if (o1.getBalance() != null && o2.getBalance() == null) {
				return 1;
			}

			if (o1.getBalance() == null && o2.getBalance() != null) {
				return -1;
			}

			if (o1.getBalance() != null && o2.getBalance() != null) {
				return o1.getBalance().compareTo(o2.getBalance());
			}
		}
		return 0;
	}

}
