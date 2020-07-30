package com.ex.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ex.model.Particular;
import com.ex.model.voucher.ChequeVoucher;

@Repository
public interface ParticularRepo extends JpaRepository<Particular, Long> {

}
