package com.ex.service;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Currency;

import org.springframework.stereotype.Service;

@Service
public class UtilityService {

	public String getISODate(ZonedDateTime date) {
		return DateTimeFormatter.ofPattern("MMMM dd, yyyy").format(date);
	}

	public String getCurrencySymbol(Currency currency) {
		return currency.getSymbol();
	}

//	public String getPayeeCompleteName(Payee payee, Boolean longg) {
//		if (payee.getMiddlename() == null) {
//			return payee.getFirstname() + " " +
//					 payee.getLastname();
//		}
//		
//		if (longg) {	
//			return payee.getFirstname() + " " + 
//				payee.getMiddlename() + " " +
//			 		payee.getLastname();
//		}
//		
//		return payee.getFirstname() + " " + 
//			payee.getMiddlename().substring(0, 1) + " " +
//				payee.getLastname();
//	}
}
