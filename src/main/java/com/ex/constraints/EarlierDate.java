package com.ex.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
@Constraint(validatedBy = {EarlierDateValidator.class })
public @interface EarlierDate {
	String message() default "Must be greater than {value}";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
//    int year();
//    int month();
//    int day();
    String value();
    
//    @Target({ FIELD })
//    @Retention(RUNTIME)
//    @Documented
//    @interface List {
////    	EarlierDate[] year();
////    	EarlierDate[] month();
////    	EarlierDate[] day();
//    	
//    }
}
