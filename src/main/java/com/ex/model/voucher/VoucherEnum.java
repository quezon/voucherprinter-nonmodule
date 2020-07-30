package com.ex.model.voucher;

import java.util.HashMap;
import java.util.Map;

public enum VoucherEnum implements VoucherableEnum {
	CASH_VOUCHER("cash_voucher.pdf", 1, "cav", "cash", CashVoucher.class),
	CHEQUE_VOUCHER("cheque_voucher.pdf", 2, "chv", "cheque", ChequeVoucher.class),
	PETTY_CASH_VOUCHER("pettycash_voucher.pdf", 3, "pcv", "pettycash", PettyCashVoucher.class);
	
	public final String filename;
    public final int order;
    public final String threeLetterCode;
    public final String propCode;
    public final Class clazz;
	
	private VoucherEnum(String filename, int order, String threeLetterCode, String propCode, Class clazz) {
		this.filename = filename;
		this.order = order;
		this.threeLetterCode = threeLetterCode;
		this.propCode = propCode;
		this.clazz = clazz;
	}
	
	private static final Map<Class, VoucherEnum> clazzMap = new HashMap<Class, VoucherEnum>();

    static {
        for (VoucherEnum ve : VoucherEnum.values()) {
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
	
	public static String getFileNameByClazz(Class clazz) {
		return clazzMap.get(clazz).getFilename();
	}
	
	public static String getThreeLetterCodeByClazz(Class clazz) {
		return clazzMap.get(clazz).getThreeLetterCode();
	}
	
	public static String getPropCodeByClazz(Class clazz) {
		return clazzMap.get(clazz).getPropCode();
	}
	
	public static VoucherEnum getEnumByClazz(Class clazz) {
		return clazzMap.get(clazz);
	}
	
	public String getPropCode() {
		return this.propCode;
	}
	
	public Class getClazz() {
		return this.clazz;
	}
	
	 
	
	public static void main(String[] args) {
		String str =VoucherEnum.getThreeLetterCodeByClazz(CashVoucher.class);
		String str2 =VoucherEnum.getThreeLetterCodeByClazz(ChequeVoucher.class);
		String str3 =VoucherEnum.getThreeLetterCodeByClazz(PettyCashVoucher.class);
		String str4 =VoucherEnum.getPropCodeByClazz(CashVoucher.class);
		String str5 =VoucherEnum.getPropCodeByClazz(ChequeVoucher.class);
		String str6 =VoucherEnum.getPropCodeByClazz(PettyCashVoucher.class);
		System.out.println(str);
		System.out.println(str2);
		System.out.println(str3);
		System.out.println(str4);
		System.out.println(str5);
		System.out.println(str6);
	}
}
