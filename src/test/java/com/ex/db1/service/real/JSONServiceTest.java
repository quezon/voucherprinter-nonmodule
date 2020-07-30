package com.ex.db1.service.real;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ex.model.voucher.CashVoucher;
import com.ex.service.JSONService;

public class JSONServiceTest {
	@Autowired
	JSONService jsonService;
	
	CashVoucher cv;
	
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testConvertVoucherToJSON() {
		
	}
	
}
