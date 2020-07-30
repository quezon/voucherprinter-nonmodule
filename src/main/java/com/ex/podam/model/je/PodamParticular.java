package com.ex.podam.model.je;

import lombok.Data;

@Data
public class PodamParticular {
	private long id;
	private String description;
	private String docReferred;
	private String docNumber;
	private double amount;
	
	public PodamParticular(long id, String description, String docReferred, String docNumber, double amount) {
		super();
		this.id = id;
		this.description = description;
		this.docReferred = docReferred;
		this.docNumber = docNumber;
		this.amount = amount;
	}
	
	
}
