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
import com.ex.model.voucher.PettyCashVoucher;
import com.ex.service.voucher.PettyCashVoucherService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/pcv")
public class PettyCashController {
	@Autowired
	PettyCashVoucherService voucherService;

	@PostMapping("/edit")
	public boolean editVoucher(@RequestBody PettyCashVoucher pcv)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		return voucherService.save(pcv, pcv.getId());
	}

	@PostMapping("/save")
	public Long saveVouchers(@RequestBody PettyCashVoucher pcv) {
		return voucherService.save(pcv);
	}

	@PostMapping("/edit/print")
	public boolean editPrintVoucher(@RequestBody PettyCashVoucher pcv)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		return voucherService.editPrintVoucher(pcv);
	}

	@PostMapping("/save/print")
	public Long savePrintVoucher(@RequestBody PettyCashVoucher pcv) {
		return voucherService.savePrintVoucher(pcv);
	}

	@GetMapping("/get/{voucherId}")
	public PettyCashVoucher getVoucherBalance(@PathVariable("voucherId") Long voucherId) {
		return voucherService.getPettyCashVoucherRepo().getOne(voucherId);
	}

	@GetMapping("/gets")
	public List<PettyCashVoucher> getAllVouchers() {
		return voucherService.getAll();
	}

	@GetMapping("/get/date")
	public List<PettyCashVoucher> getAllVouchersByDateBetween(@RequestBody DateRange dr) throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String methodName = "findAllBy" + dr.getDateName() + "Between";
		return voucherService.getAllByDateBetween(dr.getStart(), dr.getEnd(), methodName);
	}
}
