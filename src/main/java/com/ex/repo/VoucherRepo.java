package com.ex.repo;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoucherRepo<T> extends JpaRepository<T,Long>{
	public List<T> findAllByDateCreatedBetween(ZonedDateTime dateFrom, ZonedDateTime dateTo);
	public List<T> findAllByDatePrintedBetween(ZonedDateTime dateFrom, ZonedDateTime dateTo);
	public List<T> findAllByDateApprovedBetween(ZonedDateTime dateFrom, ZonedDateTime dateTo);
	public List<T> findAllByDateRejectedBetween(ZonedDateTime dateFrom, ZonedDateTime dateTo);
	public List<T> findAllByDateSentBetween(ZonedDateTime dateFrom, ZonedDateTime dateTo);
	public List<T> findAllByDateVoidedBetween(ZonedDateTime dateFrom, ZonedDateTime dateTo);

}
