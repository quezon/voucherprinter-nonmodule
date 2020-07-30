package com.ex.model.financial;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class Credit {
	double amount;
	String credit;
	String chartOfAccountCode;
}
