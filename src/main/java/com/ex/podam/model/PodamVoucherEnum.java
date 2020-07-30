package com.ex.podam.model;

import java.util.HashMap;
import java.util.Map;

import com.ex.model.voucher.CashVoucher;
import com.ex.model.voucher.ChequeVoucher;
import com.ex.model.voucher.PettyCashVoucher;
import com.ex.model.voucher.VoucherableEnum;

public enum PodamVoucherEnum implements VoucherableEnum {
	CASH_VOUCHER("cash_voucher.pdf", 1, "cav", "cash", PodamCashVoucher.class),
	CHEQUE_VOUCHER("cheque_voucher.pdf", 2, "chv", "cheque", PodamChequeVoucher.class),
	PETTY_CASH_VOUCHER("pettycash_voucher.pdf", 3, "pcv", "pettycash", PodamPettyCashVoucher.class);
	
	public final String filename;
    public final int order;
    public final String threeLetterCode;
    public final String propCode;
    public final Class clazz;
	
	private PodamVoucherEnum(String filename, int order, String threeLetterCode, String propCode, Class clazz) {
		this.filename = filename;
		this.order = order;
		this.threeLetterCode = threeLetterCode;
		this.propCode = propCode;
		this.clazz = clazz;
	}
	
	private static final Map<Class, PodamVoucherEnum> clazzMap = new HashMap<Class, PodamVoucherEnum>();

    static {
        for (PodamVoucherEnum ve : PodamVoucherEnum.values()) {
        	clazzMap.put(ve.getClazz(), ve);
        }
    }
	
	public String getFilename() {
		return this.filename;
	}
	
	public int getOrder() {
		return this.order;
	}
	
	public String getThreeLetterCode() {
		return this.threeLetterCode;
	}
	
	public static String getThreeLetterCodeByClazz(Class clazz) {
		return clazzMap.get(clazz).getThreeLetterCode();
	}
	
	public static String getPropCodeByClazz(Class clazz) {
		return clazzMap.get(clazz).getPropCode();
	}
	
	public static PodamVoucherEnum getEnumByClazz(Class clazz) {
		return clazzMap.get(clazz);
	}
	
	public String getPropCode() {
		return this.propCode;
	}
	
	public Class getClazz() {
		return this.clazz;
	}
}
