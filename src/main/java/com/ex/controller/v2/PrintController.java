package com.ex.controller.v2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ex.service.voucher.VoucherService;

@RestController
public class PrintController {
	@Autowired
	VoucherService voucherService;
	
	@PostMapping("/print")
	public boolean printVouchers(@RequestBody List<String> fileNames) {
		return voucherService.printVoucher(fileNames);
	}
}
