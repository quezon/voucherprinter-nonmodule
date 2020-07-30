package com.ex.controller.v2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.podam.PodamCreditBuilder;
import com.ex.podam.PodamParticularBuilder;
import com.ex.podam.model.PodamCashVoucher;
import com.ex.podam.model.PodamChequeVoucher;
import com.ex.podam.model.PodamPettyCashVoucher;
import com.ex.service.JSONService;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@RestController
public class SampleController {

	PodamFactory factory = new PodamFactoryImpl();
	PodamCashVoucher cashVoucher = factory.manufacturePojoWithFullData(PodamCashVoucher.class);
	PodamChequeVoucher chequeVoucher = factory.manufacturePojoWithFullData(PodamChequeVoucher.class);
	PodamPettyCashVoucher pettyCashVoucher = factory.manufacturePojoWithFullData(PodamPettyCashVoucher.class);

	PodamCreditBuilder pcb1 = new PodamCreditBuilder(cashVoucher);
	PodamCreditBuilder pcb2 = new PodamCreditBuilder(chequeVoucher);
	PodamCreditBuilder pcb3 = new PodamCreditBuilder(pettyCashVoucher);
	PodamParticularBuilder ppb1 = new PodamParticularBuilder(cashVoucher, 3);
	PodamParticularBuilder ppb2 = new PodamParticularBuilder(chequeVoucher, 3);
	PodamParticularBuilder ppb3 = new PodamParticularBuilder(pettyCashVoucher, 3);

	@Autowired
	JSONService json;

	@GetMapping(value = "/show/sample/cav", produces = "application/json")
	public PodamCashVoucher getCashVoucherSample() {
		return cashVoucher;
	}

	@GetMapping(value = "/show/sample/chv", produces = "application/json")
	public PodamChequeVoucher getChequeVoucherSample() {
		return chequeVoucher;
	}

	@GetMapping(value = "/show/sample/pcv", produces = "application/json")
	public PodamPettyCashVoucher getPettyCashVoucherSample() {
		return pettyCashVoucher;
	}

	@GetMapping(value = "/show/sample/template/xdoc/cav", produces = "application/json")
	public String getJSONStringCashVoucher() {
		return json.convertVoucherToJSONStringForXDoc(cashVoucher);
	}

	@GetMapping(value = "/show/sample/template/xdoc/chv", produces = "application/json")
	public String getJSONStringChequeVoucher() {
		return json.convertVoucherToJSONStringForXDoc(chequeVoucher);
	}

	@GetMapping(value = "/show/sample/template/xdoc/pcv", produces = "application/json")
	public String getJSONStringPettyCashVoucher() {
		return json.convertVoucherToJSONStringForXDoc(pettyCashVoucher);
	}
}
