package com.ex.advice;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ex.exc.*;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler({DateNotNullException.class, InvalidVoucherNameException.class, VoucherExistsException.class, VoucherNotExistsException.class,
		VoucherNotValidException.class, WrongVoucherTypeException.class, Exception.class})
	public boolean handleVoucherExceptions(HttpServletRequest request, Exception ex) {
		logger.info("Voucher Exception Occurred: URL= " + request.getRequestURL());
		return false;
	}
}
