package com.ex.podam.model;

import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ex.constraints.EarlierDate;
import com.ex.model.voucher.Voucherable;
import com.ex.podam.CurrencyStrategy;
import com.ex.podam.FullnameStrategy;
import com.ex.podam.ZonedDateTimeStrategy;
import com.ex.podam.model.je.PodamJournalEntry;
import com.ex.podam.model.je.PodamParticular;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamStrategyValue;

@Data
@Getter @Setter
@MappedSuperclass
public abstract class PodamVoucher implements Voucherable {
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
	private List<PodamParticular> particulars;
	
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
	private PodamJournalEntry journalEntry;
}
