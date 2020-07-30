package com.ex.db1.model.payment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ex.model.payment.Cheque;

class ChequeTest {
	static Cheque cheque = new Cheque();
	static Currency currency;
	
	@BeforeAll
	static void setUp() throws Exception {
		currency = Currency.getInstance(new Locale("en","ph"));
		cheque.setAmount(1000);
		cheque.setCurrency(currency);
		cheque.setBankAccountNumber("79786785");
		cheque.setBankRoutingNumber("06704222");
		cheque.setBankId(1);
		cheque.setBankName("TD Bank");
	}

	@Test
	void allEquals() {
		assertEquals(1000, cheque.getAmount());
		assertEquals("PHP", cheque.getCurrency().getCurrencyCode());
		assertEquals("79786785", cheque.getBankAccountNumber());
		assertEquals("06704222", cheque.getBankRoutingNumber());
		assertEquals(1, cheque.getBankId());
		assertEquals("TD Bank", cheque.getBankName());
	}

	@Test
	void unsetBooleanEqualsFalse() {
		assertFalse(cheque.isValid());
		assertFalse(cheque.isSavedOnly());
		assertFalse(cheque.isSent());
		assertFalse(cheque.isVoided());
		assertFalse(cheque.isCleared());
		
		assertFalse(cheque.isOverdraft());
		assertFalse(cheque.isReissued());
		assertFalse(cheque.isPrintedAndSaved());
	}
}
