package com.ex.podam;

import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import uk.co.jemos.podam.common.AttributeStrategy;

public class CurrencyStrategy implements AttributeStrategy<Currency> {

	Currency yen = Currency.getInstance("JPY");
	String currencyCode;
	Currency usd = yen.getInstance("USD");
	Currency php = yen.getInstance("PHP");
	
	List<Currency> names = Arrays.asList(yen,usd,php);
	Random rand = new Random();
	
	@Override
	public Currency getValue(Class attrType, List attrAnnotations) {
		int randomNumber = rand.nextInt(names.size());
		return names.get(randomNumber);
	}
	
}
