package com.ex.db1.model.payment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Currency;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ex.model.payment.Cash;

class CashTest {
	static Cash cash = new Cash();
	static Currency currency;
	
	@BeforeAll
	static void setUp() throws Exception {
		currency = Currency.getInstance(new Locale("en","ph"));
		cash.setAmount(1000);
		cash.setCurrency(currency);
	}

	@Test
	void allEquals() {
		assertEquals(1000, cash.getAmount());
		assertEquals("PHP", cash.getCurrency().getCurrencyCode());
	}

	@Test
	void unsetBooleanEqualsFalse() {
		assertFalse(cash.isValid());
		assertFalse(cash.isSavedOnly());
		assertFalse(cash.isSent());
		assertFalse(cash.isVoided());
		assertFalse(cash.isCleared());
	}
}
