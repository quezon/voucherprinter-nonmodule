package com.ex.podam.model.je;

import lombok.Data;

@Data
public class PodamCredit {
	private double amount;
	private String credit;
	private String chartOfAccountCode;

	public PodamCredit(double amount, String credit, String chartOfAccountCode) {
		super();
		this.amount = amount;
		this.credit = credit;
		this.chartOfAccountCode = chartOfAccountCode;
	}
}
