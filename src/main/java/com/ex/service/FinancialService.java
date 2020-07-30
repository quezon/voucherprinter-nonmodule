package com.ex.service;

import java.math.BigDecimal;
import java.math.MathContext;

import org.springframework.stereotype.Service;

import pl.allegro.finance.tradukisto.MoneyConverters;

@Service
public class FinancialService {
	public String getTotalCreditsInWords(Double amountNumber) {
		MoneyConverters converter = MoneyConverters.ENGLISH_BANKING_MONEY_VALUE;
		return converter.
				asWords(new BigDecimal(amountNumber, MathContext.DECIMAL64))
				.replace("£", "")
				.toUpperCase();
	}
}
