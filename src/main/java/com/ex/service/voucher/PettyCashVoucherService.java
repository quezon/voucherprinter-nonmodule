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
import com.ex.model.voucher.PettyCashVoucher;
import com.ex.repo.PettyCashVoucherRepo;

@Service
public class PettyCashVoucherService extends VoucherService {
	@Autowired
	PettyCashVoucherRepo pcvr;

	public Long save(PettyCashVoucher pcv) {
		return pcvr.save(pcv).getId();
	}

	public Boolean save(PettyCashVoucher pcv, Long voucherNumber)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		boolean pettyCashVoucherExists = pcvr.existsById(voucherNumber);

		if (pettyCashVoucherExists && voucherNumber == null) {
			throw new VoucherExistsException();
		} else if (!pettyCashVoucherExists && voucherNumber != null) {
			throw new VoucherNotExistsException();
		} else if (isValidVoucher(pcv)) {
			if (voucherNumber != null) {
				Optional<PettyCashVoucher> pettyCashVoucherFound = pcvr.findById(voucherNumber);
				pettyCashVoucherFound.ifPresent(pettyCashVoucher -> {
					// delete what is found
					pcvr.delete(pettyCashVoucher);
				});
			}
			pcvr.save((PettyCashVoucher) pcv);
			externalAPI.saveVoucherFile(pcv);
		} else {
			throw new VoucherNotValidException();
		}
		return true;
	}

	public Long savePrintVoucher(PettyCashVoucher pcv) {
		// save cash voucher in db
		Long voucherId = save(pcv);

		if (voucherId != null) {
			// save cash voucher file
			if (externalAPI.saveVoucherFile(pcv)) {
				// print cash voucher
				if (externalAPI.print(vtf.convert(pcv))) {
					return voucherId;
				}
			}
		}
		return voucherId;
	}

	public Boolean editPrintVoucher(PettyCashVoucher pcv)
			throws VoucherExistsException, VoucherNotExistsException, VoucherNotValidException {
		Boolean saved = save(pcv, pcv.getId());

		// save petty cash voucher in db
		if (saved) {
			// save petty cash voucher file
			if (externalAPI.saveVoucherFile(pcv)) {
				// print petty cash voucher
				if (externalAPI.print(vtf.convert(pcv))) {
					return saved;
				}
			}
		}

		return false;
	}

	public Boolean isValidVoucher(PettyCashVoucher pcv) {
		Boolean hasPettyCashCredit = false;

		hasPettyCashCredit = pcv.getJournalEntry().getCredits().stream()
				.anyMatch(a -> a.getCredit().equals("Petty Cash"));

		return super.isValidVoucher(pcv) && hasPettyCashCredit;
	}

	public PettyCashVoucherRepo getPettyCashVoucherRepo() {
		return pcvr;
	}

	public List<PettyCashVoucher> getAll() {
		return pcvr.findAll();
	}

	public List<PettyCashVoucher> getAllByDateBetween(ZonedDateTime from, ZonedDateTime to, String methodName)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {

		Method getAllByDateBetweenMethod = PettyCashVoucherRepo.class.getMethod(methodName, ZonedDateTime.class,
				ZonedDateTime.class);

		return (List<PettyCashVoucher>) getAllByDateBetweenMethod.invoke(pcvr, from, to);
	}
}
