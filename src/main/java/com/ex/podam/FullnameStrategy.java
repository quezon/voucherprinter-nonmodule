package com.ex.podam;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import uk.co.jemos.podam.common.AttributeStrategy;

public class FullnameStrategy implements AttributeStrategy<String> {

	List<String> names = Arrays.asList("Gabriel T Ferrer", "Jorge Campos", "Luke Nordelius", "Samuel Jones", "Dylan Boyce", "Gael Ngouana");
	Random rand = new Random();
	
	@Override
	public String getValue(Class attrType, List attrAnnotations) {
		// TODO Auto-generated method stub
		int randomNumber = rand.nextInt(names.size());
		return names.get(randomNumber);
	}
	
}
