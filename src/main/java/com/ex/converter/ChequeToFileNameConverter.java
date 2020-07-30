package com.ex.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.ex.model.payment.Cheque;

@Component
public class ChequeToFileNameConverter implements Converter<Cheque, String>{

	@Override
	public String convert(Cheque source) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<String> convert(List<Cheque> cheques) {
		List<String> strings = new ArrayList<String>();
    	for (Cheque cheque: cheques) {
    		strings.add( convert(cheque) );
    	}
    	
    	return strings;
	}

}
