package com.ex.converter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.ex.model.voucher.Voucher;
import com.ex.model.voucher.VoucherEnum;

@Primary
@Component
public class VoucherToFileNameConverter2 extends VoucherToFileNameConverter{
	
	@Value("${spring.sequence.voucher.digits}")
	String digits;
	
	@Value("${spring.directory.voucher}")
	String saveVoucherTo;
	
	@Value("${spring.external.url.doc.name}")
	String urlToGetVoucherFileName;
	
    @Override
    public String convert(Voucher from) {
    	StringBuilder sb = new StringBuilder(saveVoucherTo);
    	String endsWith = VoucherEnum.getFileNameByClazz(from.getClass());
    	sb.append(endsWith);
    	sb.insert(endsWith.indexOf("."), String.format("%0"+digits+"d", from.getId()));
    	
    	return sb.toString();
    }
}
