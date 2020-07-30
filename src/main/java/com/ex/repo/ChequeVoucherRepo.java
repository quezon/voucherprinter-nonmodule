package com.ex.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.model.voucher.ChequeVoucher;

@Repository
public interface ChequeVoucherRepo extends VoucherRepo<ChequeVoucher>{
	public List<ChequeVoucher> findByDateApprovedIsNullAndDatePrintedIsNull();
	
	public default void setAll(List<ChequeVoucher> vouchers, String stat) {
		vouchers.forEach((voucher) -> {
			if (stat == "valid") {
				voucher.setValid(true);
			} else if (stat == "printed and saved") {
				voucher.setPrinted(true);
				voucher.setSaved(true);
			} else if (stat == "saved") {
				voucher.setSaved(true);
			} else if (stat == "void") {
				voucher.setVoided(true);
			} else if (stat == "paid") {
				voucher.setPaid(true);
			}
		});
		
		saveAll(vouchers);
	}
}
