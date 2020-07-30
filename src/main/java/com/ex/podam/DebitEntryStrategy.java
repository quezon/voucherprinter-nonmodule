package com.ex.podam;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import com.ex.podam.model.je.PodamDebit;

import uk.co.jemos.podam.common.AttributeStrategy;

public class DebitEntryStrategy implements AttributeStrategy<PodamDebit> {
	Random random = new Random();
	double amount = random.nextInt(49900) + 100.0;
	DebitAccounts das = new DebitAccounts();
	List<PodamDebit> debitss = new ArrayList<>();
	List<String> codes = Arrays.asList("201", "301", "302", "303", "401", "402");
	Set<String> codesSelected = new HashSet<>();
	
	@Override
	public PodamDebit getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
		String acctCode;
		String acctName;
		double decrease;
		double decreaseFactor;
		
		do {
			decreaseFactor = Math.round(random.nextDouble() * 100.0) /100.0;
		} while (decreaseFactor == 0.0);

		decrease = amount * Math.round(decreaseFactor * 100.0) /100.0;
		amount = amount - decrease;
		
		do {
			acctCode = codes.get(random.nextInt(codes.size() - 1));
			if (!codesSelected.contains(acctCode)) {
				codesSelected.add(acctCode);
				break;
			}
		} while (codesSelected.contains(acctCode));

		acctName = das.das.get(acctCode);

		return new PodamDebit(decrease, acctName, acctCode);
	}
}

class DebitAccounts {
	Map<String, String> das = new HashMap<>();

	DebitAccounts() {
		das.put("201", "Inventory");
		das.put("301", "Salaries Payable");
		das.put("302", "Accounts Payable");
		das.put("303", "Notes Payable");
		das.put("401", "Utilies Expense");
		das.put("402", "Prepaid Expense");
	}
}
