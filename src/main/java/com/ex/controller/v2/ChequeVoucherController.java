package com.ex.controller.v2;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ex.dto.DateRange;
import com.ex.exc.VoucherExistsException;
import com.ex.exc.VoucherNotExistsException;
import com.ex.exc.VoucherNotValidException;
import com.ex.model.voucher.ChequeVoucher;
import com.ex.service.voucher.ChequeVoucherService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/chv")
public class ChequeVoucherController {
	@Autowired
	ChequeVoucherService voucherService;

	@PostMapping("/edit")
	public boolean editVoucher(@RequestBody ChequeVoucher chv)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		return voucherService.save(chv, chv.getId());
	}

	@PostMapping("/save")
	public ChequeVoucher saveVoucher(@RequestBody ChequeVoucher chv) {
		return voucherService.save(chv);
	}

	@PostMapping("/print")
	public Long printVoucher(@RequestBody ChequeVoucher chv) {
		return 0L;
	}

	@PostMapping("/edit/print")
	public boolean editPrintVoucher(@RequestBody ChequeVoucher chv)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		return voucherService.editPrintVoucher(chv);
	}

	@PostMapping("/save/print")
	public Long savePrintVoucher(@RequestBody ChequeVoucher chv) {
		return voucherService.savePrintVoucher(chv);
	}

	@GetMapping("/get/{voucherId}")
	public ChequeVoucher getVoucherBalance(@PathVariable("voucherId") Long voucherId) {
		return voucherService.getChequeVoucherRepo().getOne(voucherId);
	}

	@GetMapping("/gets")
	public List<ChequeVoucher> getAllVouchers() {
		return voucherService.getAll();
	}

	@GetMapping("/gets/date")
	public List<ChequeVoucher> getAllVouchersByDateBetween(@RequestBody DateRange dr) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String methodName = "findAllBy" + dr.getDateName() + "Between";
		return voucherService.getAllByDateBetween(dr.getStart(), dr.getEnd(), methodName);
	}
}
