package com.ex.podam.model.je;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PodamDebit {
	private double amount;
	private String debit;
	private String chartOfAccountCode;
}
