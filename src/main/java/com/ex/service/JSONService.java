package com.ex.service;

import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.ex.model.voucher.Voucherable;
import com.ex.model.voucher.Voucher;
import com.ex.model.voucher.VoucherEnum;
import com.ex.model.voucher.VoucherableEnum;
import com.ex.podam.model.PodamVoucher;
import com.ex.podam.model.PodamVoucherEnum;
import com.google.gson.Gson;

@Service
public class JSONService {
	@Autowired
	private Environment env;
	
	public JSONObject mergeJSONObjects(JSONObject paramFiles, JSONObject reportBody) {
		JSONObject mergedJSON = new JSONObject();
		mergedJSON.put("paramFiles", paramFiles);
		mergedJSON.put("reportBody", reportBody);
		return mergedJSON;
	}
	
	public <T extends Voucherable> JSONObject convertVoucherToJSON(T voucher) {
		String jsonInString = new Gson().toJson(voucher);
		JSONObject mJSONObject = new JSONObject(jsonInString);
		return mJSONObject;
	}
	
	public void supplyMostCommonKeys(JSONObject jobj, VoucherableEnum voucherEnum, String date) {
		Long fileNumber = jobj.getJSONObject(voucherEnum.getThreeLetterCode()).getLong("id");
		
		jobj.put("endsWith", env.getProperty("spring.template.xdoc.voucher." + voucherEnum.getPropCode() + ".endsWith"));
		jobj.put("parentFolder", env.getProperty("spring.directory.voucher"));
		jobj.put("fileNumber", fileNumber.toString());
		jobj.put("template", env.getProperty("spring.template.xdoc.voucher." + voucherEnum.getPropCode()));
		jobj.put("date", date);
	}
	
	public String convertVoucherToJSONStringForXDoc(Voucherable voucher) {
		PodamVoucherEnum pve = null;
		VoucherEnum ve = null;
		String threeLetter;
		JSONObject jsonToReturn = new JSONObject();
		JSONObject jsonVoucher = convertVoucherToJSON(voucher);
		String dateString = voucher.getDateCreated().format(DateTimeFormatter.ofPattern("MMM dd, yyyy"));
		
		if(voucher instanceof PodamVoucher) {
			threeLetter = PodamVoucherEnum.getThreeLetterCodeByClazz(voucher.getClass());
			pve = PodamVoucherEnum.getEnumByClazz(voucher.getClass());
			jsonToReturn.put(threeLetter, jsonVoucher);
			supplyMostCommonKeys(jsonToReturn, pve, dateString);
		} else if (voucher instanceof Voucher) {
			threeLetter = VoucherEnum.getThreeLetterCodeByClazz(voucher.getClass());
			ve = VoucherEnum.getEnumByClazz(voucher.getClass());
			jsonToReturn.put(threeLetter, jsonVoucher);
			supplyMostCommonKeys(jsonToReturn, ve, dateString);
		}
		
		return jsonToReturn.toString();
	}
}
