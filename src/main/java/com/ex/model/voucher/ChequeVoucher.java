package com.ex.model.voucher;

import java.util.List;

import javax.persistence.Entity;

import com.ex.model.payment.Cheque;

import lombok.Data;

/**
 * 
 * @author Gabriel Ferrer Can have as many types of expenses per cheque voucher
 */
@Entity
@Data
//@EqualsAndHashCode(callSuper = false)
public class ChequeVoucher extends Voucher {
	List<Cheque> cheques;
}
