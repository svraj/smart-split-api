package com.triumsys.split.service.comparators;

import java.util.Comparator;

import com.triumsys.split.data.entity.ExpenseSplit;

public class ExpenseSplitAmountComparator implements Comparator<ExpenseSplit>{

	@Override
	public int compare(ExpenseSplit o1, ExpenseSplit o2) {
		if(o1==null && o2 ==null){
			return 0;
		}
		
		if(o1!=null && o2==null){
			return 1;
		}
		
		if(o1==null && o2 !=null){
			return -1;
		}
		
		if(o1!=null && o2!=null){
			
			if(o1.getAmount()==null && o2.getAmount() ==null){
				return 0;
			}
			
			if(o1.getAmount()!=null && o2.getAmount()==null){
				return 1;
			}
			
			if(o1.getAmount()==null && o2.getAmount() !=null){
				return -1;
			}
			
			if(o1.getAmount()!=null && o2.getAmount()!=null){
				return o1.getAmount().compareTo(o2.getAmount());
			}
		}
		return 0;
	}

}
