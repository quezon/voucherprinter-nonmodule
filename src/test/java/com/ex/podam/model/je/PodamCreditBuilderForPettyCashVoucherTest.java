package com.ex.podam.model.je;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import com.ex.podam.PodamCreditBuilder;
import com.ex.podam.model.PodamPettyCashVoucher;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class PodamCreditBuilderForPettyCashVoucherTest {
	PodamFactory factory;
	PodamPettyCashVoucher cv;
	List<PodamDebit> debits;
	List<PodamCredit> credits;
	
	
	@Before
	public void setUp() {
		factory = new PodamFactoryImpl();
		cv = factory.manufacturePojo(PodamPettyCashVoucher.class);
		new PodamCreditBuilder(cv);
		debits = cv.getJournalEntry().getDebits();
		credits = cv.getJournalEntry().getCredits();
	}
	
	@Test
	public void randomTotalDebitsEqualTotalCredits() {
		double totaldebits = 0.0;
		double totalcredits = 0.0;
		
		for(PodamDebit debit: debits) {
			totaldebits += debit.getAmount();
		}
		
		for(PodamCredit credit: credits) {
			totalcredits += credit.getAmount();
		}
		
		assertTrue(totaldebits == totalcredits);
	}
	
	@Test
	public void debitsAreUnique() {
		Set<String> codes = new TreeSet<>();
		for(PodamDebit debit: debits) {
			codes.add( debit.getChartOfAccountCode() );
		}
		
		assertTrue(codes.size() == debits.size());	
	}
	
	@Test
	public void creditsAreUnique() {
		Set<String> codes = new TreeSet<>();
		for(PodamCredit credit: credits) {
			codes.add( credit.getChartOfAccountCode() );
		}
		
		assertTrue(codes.size() == credits.size());	
	}
}
