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
import com.ex.model.voucher.ChequeVoucher;
import com.ex.repo.ChequeVoucherRepo;

@Service
public class ChequeVoucherService extends VoucherService {
	@Autowired
	ChequeVoucherRepo chvr;

	public ChequeVoucher save(ChequeVoucher chv) {
		ChequeVoucher chv2 = chvr.save(chv);
		if (chv2 != null) {
			externalAPI.saveVoucherFile(chv2);
			externalAPI.saveChequeFiles(chv2.getCheques());
		}
		return chv2;
	}

	public Boolean save(ChequeVoucher chv, Long voucherNumber)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		Boolean chequeVoucherExists = chvr.existsById(voucherNumber);

		if (chequeVoucherExists && voucherNumber == null) {
			throw new VoucherExistsException();
		} else if (!chequeVoucherExists && voucherNumber != null) {
			throw new VoucherNotExistsException();
		} else if (isValidVoucher(chv)) {
			if (voucherNumber != null) {
				Optional<ChequeVoucher> chequeVoucherFound = chvr.findById(voucherNumber);
				chequeVoucherFound.ifPresent(chequeVoucher -> {
					// delete what is found
					chvr.delete(chequeVoucher);
				});
			}
			chvr.save(chv);
			externalAPI.saveVoucherFile(chv);
			externalAPI.saveChequeFiles(chv.getCheques());
		} else {
			throw new VoucherNotValidException();
		}
		return true;
	}

	public Long savePrintVoucher(ChequeVoucher chv) {
		// save cheque voucher in db
		Long voucherId = save(chv).getId();

		if (voucherId != null) {
			// save cheque voucher file
			if (externalAPI.saveVoucherFile(chv)) {
				// print cheque voucher
				if (externalAPI.print(vtf.convert(chv))) {
					return voucherId;
				}
			}
		}

		return voucherId;
	}

	public Boolean editPrintVoucher(ChequeVoucher chv)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		Boolean saved = save(chv, chv.getId());

		if (saved) {
			// save cheque voucher file
			if (externalAPI.saveVoucherFile(chv)) {
				// print cheque voucher
				if (externalAPI.print(vtf.convert(chv))) {
					return saved;
				}
			}
		}

		return false;
	}

	public Boolean isValidVoucher(ChequeVoucher chv) {
		Boolean hasSpecificCreditAccounts = false;

		hasSpecificCreditAccounts = chv.getJournalEntry().getDebits().stream().anyMatch(a -> {
			return a.getDebit().equals("Interest-bearing checking accounts")
					|| a.getDebit().equals("Money market checking accounts")
					|| a.getDebit().equals("Free checking accounts");
		});

		return super.isValidVoucher(chv) && hasSpecificCreditAccounts;
	}

	public ChequeVoucherRepo getChequeVoucherRepo() {
		return chvr;
	}

	public List<ChequeVoucher> getAll() {
		return chvr.findAll();
	}

	public List<ChequeVoucher> getAllByDateBetween(ZonedDateTime from, ZonedDateTime to, String methodName)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Method getAllByDateBetweenMethod = ChequeVoucherRepo.class.getMethod(methodName, ZonedDateTime.class,
				ZonedDateTime.class);

		return (List<ChequeVoucher>) getAllByDateBetweenMethod.invoke(chvr, from, to);
	}
}
