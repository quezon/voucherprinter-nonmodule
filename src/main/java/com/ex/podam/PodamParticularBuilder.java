package com.ex.podam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import com.ex.podam.model.PodamCashVoucher;
import com.ex.podam.model.PodamVoucher;
import com.ex.podam.model.je.PodamParticular;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;


public class PodamParticularBuilder {
	private Random random = new Random();
	private AtomicLong al = new AtomicLong(1L);
	
	public PodamParticularBuilder(PodamVoucher voucher, int numberOfRecords) {
		int numberOfParticulars = random.nextInt(numberOfRecords - 1) + 1;
		List<PodamParticular> particulars = new ArrayList<>();
		Map<String,String> docmap = new HashMap<>();
		double total = voucher.getJournalEntry().getTotalDebits();
		double decrease = 0.0;
		String[] stringArray = new String[2];
		
		for(int i=0;i<numberOfParticulars;i++) {
			do {
				decrease = total * Math.round(random.nextDouble() * 100.0) / 100.0;
			} while(decrease == 0.0);
			
			docmap = RandomDoc.get();
			stringArray = RandomDocNumber.get(docmap);
			
			if (i < numberOfParticulars - 1) {
				total -= decrease;
			} else {
				decrease = total;
			}
			
			particulars.add( new PodamParticular(al.getAndIncrement(), RandomDescription.get() ,stringArray[1], stringArray[0], decrease) );
		}
		voucher.setParticulars(particulars);
	}
	
	public static void main(String[] args) {
		PodamFactory factory;
		PodamCashVoucher cv;
		
		factory = new PodamFactoryImpl();
		cv = factory.manufacturePojo(PodamCashVoucher.class);
		new PodamCreditBuilder(cv);
		new PodamParticularBuilder(cv, 5);
		
		for(PodamParticular par: cv.getParticulars()) {
			System.out.print(par.getDocNumber() + "  ");
			System.out.print(par.getDocReferred() + "  ");
			System.out.print(par.getDescription() + "  ");
			System.out.println(par.getAmount());
		}
	}
}

class RandomDoc {
	static Random random = new Random();
	static Map<Integer,Map<String,String>> docs = new HashMap<>();
	static Map<String,String> inv = new HashMap<>();
	static Map<String,String> ub = new HashMap<>();
	
	public static Map<String,String> get() {
		int index = random.nextInt(1)+1; 
		
		inv.put("INV","Purchase Invoice");
		ub.put("UB","Utility Bill");
		
		docs.put(1, inv);
		docs.put(2, ub);
		
		return docs.get(index);
	}
}

class RandomDescription {
	static Random random = new Random();
	
	static String[] descriptions = new String[10];
	
	public static String get() {
		int idx = random.nextInt(9);
		descriptions[0] = "Purchase of Iron tubes";
		descriptions[1] = "Payment of Electricity Bill";
		descriptions[2] = "Payment of Notes due";
		descriptions[3] = "Purchase of Mobile load";
		descriptions[4] = "Payment of Rental";
		descriptions[5] = "Purchase of Concrete blocks";
		descriptions[6] = "Payment of Employees salary";
		descriptions[7] = "Purchase of Computers";
		descriptions[8] = "Purchase of Equipment";
		descriptions[9] = "Purchase of Metal Sheets";
		
		return descriptions[idx];
	}
}

class RandomDocNumber {
	static Random random = new Random();
	public static String[] get(Map<String,String> docmap) {
		String[] result = new String[2];
		StringBuilder sb = new StringBuilder();
		String leading = String.format("%06d", random.nextInt(999998) + 1);
		String oneKey = null;
		
		for(String key: docmap.keySet()) {
			oneKey = key;
			sb.append(key);
			sb.append(leading);
		}
		
		result[0] = sb.toString();
		result[1] = docmap.get(oneKey);
		
		return result;
	}
}