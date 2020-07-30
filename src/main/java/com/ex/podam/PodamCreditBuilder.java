package com.ex.podam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.ex.podam.model.PodamCashVoucher;
import com.ex.podam.model.PodamChequeVoucher;
import com.ex.podam.model.PodamPettyCashVoucher;
import com.ex.podam.model.PodamVoucher;
import com.ex.podam.model.je.PodamCredit;
import com.ex.podam.model.je.PodamDebit;
import com.ex.podam.model.je.PodamJournalEntry;

public class PodamCreditBuilder {
	public <T extends PodamVoucher> PodamCreditBuilder(T podamvoucher) {
		List<PodamCredit> credits = null;
		
		PodamJournalEntry pje = podamvoucher.getJournalEntry();
		PodamChequeVoucherCreditBuilder pChequeVoucherCreditBuilder = 
				new PodamChequeVoucherCreditBuilder(pje.getDebits());
		PodamCashVoucherCreditBuilder pCashVoucherCreditBuilder = 
				new PodamCashVoucherCreditBuilder(pje.getDebits());
		PodamPettyCashVoucherCreditBuilder pPettyCashVoucherCreditBuilder = 
				new PodamPettyCashVoucherCreditBuilder(pje.getDebits());
		
		if (podamvoucher instanceof PodamChequeVoucher) {
			credits = pChequeVoucherCreditBuilder.credits;
		} else if (podamvoucher instanceof PodamCashVoucher) {
			credits = pCashVoucherCreditBuilder.credits;
		} else if (podamvoucher instanceof PodamPettyCashVoucher) {
			credits = pPettyCashVoucherCreditBuilder.credits;
		}
		
		pje.setCredits(credits);
		podamvoucher.setJournalEntry(pje);
	}
}

class PodamChequeVoucherCreditBuilder {
	Random random = new Random();
	List<PodamCredit> credits = new ArrayList<PodamCredit>();
	PodamCredit[] pc = new PodamCredit[3];
	
	PodamChequeVoucherCreditBuilder(List<PodamDebit> debits) {
		pc[0] = new PodamCredit(0.0, "Interest-bearing checking accounts", "102");
		pc[1] = new PodamCredit(0.0, "Money market checking accounts", "103");
		pc[2] = new PodamCredit(0.0, "Free checking accounts", "104");
		
		int chose;
		int size = random.nextInt(2) + 1;
		
		Set<Integer> chosen = new HashSet<Integer>();
		for(int i=0; i < size; i++) {
			do {
				chose = random.nextInt(2);
			} while (chosen.contains(chose));
			
			chosen.add( chose );
			this.credits.add( pc[chose] );
		}
		
		allocateAmount(debits);
	}
	
	public double computeAmount(double amount) {
		double decreaseFactor;
		
		do {
			decreaseFactor = random.nextDouble();
		} while(decreaseFactor == 0.0);
		
		amount = decreaseFactor * amount;
		
		return Math.round(amount * 100.0) / 100.0;
	}
	
	public void allocateAmount(
			List<PodamDebit> debits
			) {
		double getTotalDebits = 0.0;
		double decrease = 0.0;
		
		for (PodamDebit debit: debits) {
			getTotalDebits += debit.getAmount();
		}
		
		for(int i = 0; i < this.credits.size(); i++) {
			PodamCredit pcvc = this.credits.get(i);
			if (i == credits.size() - 1) {
				pcvc.setAmount(getTotalDebits);
				credits.set(i, pcvc);
			} else {
				decrease = computeAmount(getTotalDebits);
				getTotalDebits -= decrease;
				pcvc.setAmount(decrease);
				credits.set(i, pcvc);
			}
		}
	}
}

class PodamCashVoucherCreditBuilder {
	List<PodamCredit> credits = new ArrayList<PodamCredit>();
	PodamCashVoucherCreditBuilder(List<PodamDebit> pdbs) {
		double totalCredits = 0.0;
		for(PodamDebit pdb: pdbs) {
			totalCredits += pdb.getAmount();
		}	
		credits.add( new PodamCredit(totalCredits, "Cash On Hand", "100") );
	}
}

class PodamPettyCashVoucherCreditBuilder {
	List<PodamCredit> credits = new ArrayList<PodamCredit>();
	PodamPettyCashVoucherCreditBuilder(List<PodamDebit> pdbs) {
		double totalCredits = 0.0;
		for(PodamDebit pdb: pdbs) {
			totalCredits += pdb.getAmount();
		}	
		credits.add( new PodamCredit(totalCredits, "Petty Cash", "101") );
	}
}