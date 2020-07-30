package com.ex.podam.model.je;

import java.util.List;

import com.ex.podam.DebitEntryStrategy;

import lombok.Data;
import uk.co.jemos.podam.common.PodamCollection;

@Data
public class PodamJournalEntry {
	@PodamCollection(nbrElements = 2, collectionElementStrategy = DebitEntryStrategy.class)
	List<PodamDebit> debits;
	List<PodamCredit> credits;
	
	public double getTotalDebits() {
		double total = 0.0;
		for(PodamDebit debit: debits) {
			total += debit.getAmount();
		}
		return total;
	}
	
	public double getTotalCredits() {
		double total = 0.0;
		for(PodamCredit credit: credits) {
			total += credit.getAmount();
		}
		return total;
	}
}
