package com.ex.gen;

import com.ex.model.voucher.ChequeVoucher;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;

public class ChequeVoucherJSON {
	public static void main(String[] args) {
		JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();
		schemaFactory.setAutoPutDollarSchema(true);
		JsonNode productSchema = schemaFactory.createSchema(ChequeVoucher.class);
		System.out.println(productSchema);
	}
}
