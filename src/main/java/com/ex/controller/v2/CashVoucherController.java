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
import com.ex.model.voucher.CashVoucher;
import com.ex.service.voucher.CashVoucherService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cav")
public class CashVoucherController {
	@Autowired
	CashVoucherService voucherService;

	@PostMapping("/edit")
	public boolean editVoucher(@RequestBody CashVoucher cav)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		return voucherService.save(cav, cav.getId());
	}

	@PostMapping("/save")
	public Long saveVoucher(@RequestBody CashVoucher cav) {
		return voucherService.save(cav);
	}

	@PostMapping("/edit/print")
	public boolean editPrintVoucher(@RequestBody CashVoucher cav)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		return voucherService.editPrintVoucher(cav);
	}

	@PostMapping("/save/print")
	public Long savePrintVoucher(@RequestBody CashVoucher cav) {
		return voucherService.savePrintVoucher(cav);
	}

	@GetMapping("/get/{voucherId}")
	public CashVoucher getVoucherBalance(@PathVariable("voucherId") Long voucherId) {
		return voucherService.getCashVoucherRepo().getOne(voucherId);
	}

	@GetMapping("/gets")
	public List<CashVoucher> getAllVouchers() {
		return voucherService.getAll();
	}

	@GetMapping("/gets/date")
	public List<CashVoucher> getAllVouchersByDateBetween(@RequestBody DateRange dr) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String methodName = "findAllBy" + dr.getDateName() + "Between";
		return voucherService.getAllByDateBetween(dr.getStart(), dr.getEnd(), methodName);
	}
}
