package com.ex.podam;

import java.lang.annotation.Annotation;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import uk.co.jemos.podam.common.AttributeStrategy;

public class ZonedDateTimeStrategy implements AttributeStrategy<ZonedDateTime> {

	Random random = new Random();
	
	@Override
	public ZonedDateTime getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
		int latestYear = ZonedDateTime.now().getYear();
		int year = random.nextInt((latestYear - 2000) + 1) + 2000;
		int month = random.nextInt(11) + 1;
		int day = random.nextInt(30) + 1;
		int hour = random.nextInt(23);
		int minute = random.nextInt(59);
		int second = random.nextInt(59);
		int nanoOfSecond = random.nextInt(999_999_999);
		if (month == 2) {
			if (year % 4 == 0) {
				day = random.nextInt(28) + 1;
			} else {
				day = random.nextInt(27) + 1;
			}
		}
		
		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				day = random.nextInt(30) + 1;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				day = random.nextInt(29) + 1;
				break;
		}
		
		return ZonedDateTime.of(year, month, day, hour, minute, second, nanoOfSecond, ZoneId.systemDefault());
	}
	
}
