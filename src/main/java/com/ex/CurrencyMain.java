package com.ex;

import java.util.Currency;

public class CurrencyMain {
	public static void main(String[] args) {
		System.out.println(Currency.getAvailableCurrencies().size());
		System.out.println(Currency.getAvailableCurrencies());
	}
}
