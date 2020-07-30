package com.ex.service.voucher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ex.exc.VoucherExistsException;
import com.ex.exc.VoucherNotExistsException;
import com.ex.exc.VoucherNotValidException;
import com.ex.model.voucher.CashVoucher;
import com.ex.repo.CashVoucherRepo;

@Service
public class CashVoucherService extends VoucherService {
	@Autowired
	CashVoucherRepo cavr;

	public Long save(CashVoucher cav) {
		return cavr.save(cav).getId();
	}

	public Boolean save(CashVoucher cav, Long voucherNumber)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		Boolean cashVoucherExists = cavr.existsById(voucherNumber);

		if (cashVoucherExists && voucherNumber == null) {
			throw new VoucherExistsException();
		} else if (!cashVoucherExists && voucherNumber != null) {
			throw new VoucherNotExistsException();
		} else if (isValidVoucher(cav)) {
			if (voucherNumber != null) {
				Optional<CashVoucher> cashVoucherFound = cavr.findById(voucherNumber);
				cashVoucherFound.ifPresent(cashVoucher -> {
					// delete what is found
					cavr.delete(cashVoucher);
				});
			}
			cavr.save((CashVoucher) cav);
			externalAPI.saveVoucherFile(cav);
		} else {
			throw new VoucherNotValidException();
		}
		return true;
	}

	public Long savePrintVoucher(CashVoucher cav) {
		// save cash voucher in db
		Long voucherId = save(cav);

		if (voucherId != null) {
			// save cash voucher file
			if (externalAPI.saveVoucherFile(cav)) {
				// print cash voucher
				if (externalAPI.print(vtf.convert(cav))) {
					return voucherId;
				}
			}
		}
		return voucherId;
	}

	public Boolean editPrintVoucher(CashVoucher cav)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		Boolean saved = save(cav, cav.getId());

		// save cash voucher in db
		if (saved) {
			// save cash voucher file
			if (externalAPI.saveVoucherFile(cav)) {
				// print cash voucher
				if (externalAPI.print(vtf.convert(cav))) {
					return saved;
				}
			}
		}

		return false;
	}

	public Boolean isValidVoucher(CashVoucher cav) {
		Boolean hasCashOnHandCredit = false;

		hasCashOnHandCredit = cav.getJournalEntry().getCredits().stream()
				.anyMatch(a -> a.getCredit().equals("Cash On Hand"));

		return super.isValidVoucher(cav) && hasCashOnHandCredit;
	}

	public CashVoucherRepo getCashVoucherRepo() {
		return cavr;
	}

	public List<CashVoucher> getAll() {
		return cavr.findAll();
	}

	public List<CashVoucher> getAllByDateBetween(ZonedDateTime from, ZonedDateTime to, String methodName)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Method getAllByDateBetweenMethod = CashVoucherRepo.class.getMethod(methodName, ZonedDateTime.class,
				ZonedDateTime.class);

		return (List<CashVoucher>) getAllByDateBetweenMethod.invoke(cavr, from, to);
	}
}
