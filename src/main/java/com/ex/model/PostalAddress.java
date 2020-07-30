package com.ex.model;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class PostalAddress {
	@Id
	private int id;
	private String company;
	private String streetAddress;
	private String poBox;
	private String district;
	private String city;
	private String county;
	private String postalCode;
	private String country;
}
