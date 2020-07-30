package com.ex.constraints;

import java.time.ZonedDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class EarlierDateValidator implements ConstraintValidator<EarlierDate, String> {
    private Expression exp;
    
    @Override
    public void initialize(EarlierDate earlierDateValue) {
        ExpressionParser parser = new SpelExpressionParser();
		exp = parser.parseExpression(earlierDateValue.value());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // null values are valid
        if ( value == null ) {
            return true;
        }
      
        return exp.getValue(value, Boolean.class);
        
    }
}