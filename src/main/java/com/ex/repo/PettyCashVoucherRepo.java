package com.ex.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.model.voucher.PettyCashVoucher;

@Repository
public interface PettyCashVoucherRepo extends VoucherRepo<PettyCashVoucher> {
	public List<PettyCashVoucher> findByDateApprovedIsNullAndDatePrintedIsNull();
	
	
}
