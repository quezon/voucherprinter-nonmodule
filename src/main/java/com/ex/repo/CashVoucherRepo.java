package com.ex.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.model.voucher.CashVoucher;

@Repository
public interface CashVoucherRepo extends VoucherRepo<CashVoucher> {
	public List<CashVoucher> findByDateApprovedIsNullAndDatePrintedIsNull();
}
