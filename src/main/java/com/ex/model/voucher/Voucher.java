package com.ex.model.voucher;

import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ex.constraints.EarlierDate;
import com.ex.model.Particular;
import com.ex.model.financial.JournalEntry;
import com.ex.podam.CurrencyStrategy;
import com.ex.podam.FullnameStrategy;
import com.ex.podam.ZonedDateTimeStrategy;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import uk.co.jemos.podam.common.PodamStrategyValue;



@Data
@MappedSuperclass
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Voucher implements Voucherable {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Min(1) @Max(999999)
	private long id;
	@NotNull
	@Column(nullable = false)
	@PodamStrategyValue(ZonedDateTimeStrategy.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MMM dd, YYYY")
	@EarlierDate(value="dateCreated.isBefore(datePrinted) && dateCreated.isBefore(dateApproved) && dateCreated.isBefore(dateRejected) && dateCreated.isBefore(dateSent) && dateCreated.isBefore(dateVoided)")
	private ZonedDateTime dateCreated;
	@PodamStrategyValue(FullnameStrategy.class)
	private String payee;
	@PodamStrategyValue(CurrencyStrategy.class)
	private Currency currency;
	@OneToOne
	private JournalEntry journalEntry;
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Particular> particulars;
	
	@PodamStrategyValue(FullnameStrategy.class)
	private String approvedBy;
	@PodamStrategyValue(FullnameStrategy.class)
	private String rejectedBy;
	@PodamStrategyValue(FullnameStrategy.class)
	private String preparedBy;
	@PodamStrategyValue(FullnameStrategy.class)
	private String voidedBy;
	
	@PodamStrategyValue(FullnameStrategy.class)
	private String certifiedBy;
	@PodamStrategyValue(FullnameStrategy.class)
	private String receivedBy;
	@PodamStrategyValue(FullnameStrategy.class)
	private String receivedFrom;
	
	@PodamStrategyValue(ZonedDateTimeStrategy.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MMM dd, YYYY")
	@EarlierDate(value="datePrinted.isAfter(dateCreated) && dateCreated.isBefore(dateApproved) && dateCreated.isBefore(dateRejected) && dateCreated.isBefore(dateSent) && dateCreated.isBefore(dateVoided)")
	private ZonedDateTime datePrinted;
	@PodamStrategyValue(ZonedDateTimeStrategy.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MMM dd, YYYY")
	@EarlierDate(value="dateApproved.isAfter(dateCreated) && dateApproved.isAfter(datePrinted) && dateCreated.isBefore(dateSent) && dateCreated.isBefore(dateVoided)")
	private ZonedDateTime dateApproved;
	@PodamStrategyValue(ZonedDateTimeStrategy.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MMM dd, YYYY")
	@EarlierDate(value="dateRejected.isAfter(dateCreated) && dateRejected.isAfter(datePrinted) && dateCreated.isBefore(dateSent) && dateCreated.isBefore(dateVoided)")
	private ZonedDateTime dateRejected;
	@PodamStrategyValue(ZonedDateTimeStrategy.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MMM dd, YYYY")
	@EarlierDate(value="dateRejected.isAfter(dateCreated) && dateRejected.isAfter(datePrinted) && dateCreated.isAfter(dateApproved) && dateCreated.isAfter(dateRejected)")
	private ZonedDateTime dateSent;
	@PodamStrategyValue(ZonedDateTimeStrategy.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MMM dd, YYYY")
	@EarlierDate(value="dateRejected.isAfter(dateCreated) && dateRejected.isAfter(datePrinted) && dateCreated.isAfter(dateApproved) && dateCreated.isAfter(dateRejected)")
	private ZonedDateTime dateVoided;
	
	private boolean printed;
	private boolean valid;
	private boolean saved;
	private boolean voided;
	private boolean paid;
	
//	@Transient
//	private VoucherEnum voucherType;
//	
//	public Voucher() {
//		if (this instanceof CashVoucher) {
//			this.voucherType = VoucherEnum.CASH_VOUCHER;
//		} else if (this instanceof ChequeVoucher) {
//			this.voucherType = VoucherEnum.CHEQUE_VOUCHER;
//		} else if (this instanceof PettyCashVoucher) {
//			this.voucherType = VoucherEnum.PETTY_CASH_VOUCHER;
//		}
//	}
}
