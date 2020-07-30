package com.ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ex"})
public class VoucherServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(VoucherServiceApplication.class, args);
	}
}
