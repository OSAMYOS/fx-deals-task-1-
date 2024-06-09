package com.example.FXDealsTask.validation;

import com.example.FXDealsTask.model.FxDeal;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class FxDealsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return FxDeal.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FxDeal deal = (FxDeal) target;

        if (deal.getFromCurrency() == null || deal.getFromCurrency().isEmpty()) {
            errors.rejectValue("fromCurrency", "NotNull", "From Currency ISO Code is missing.");
        } else if (deal.getFromCurrency().length() != 3) {
            errors.rejectValue("fromCurrency", "Length", "From Currency ISO Code must be exactly 3 letters.");
        }

        if (deal.getToCurrency() == null || deal.getToCurrency().isEmpty()) {
            errors.rejectValue("toCurrency", "NotNull", "To Currency ISO Code is missing.");
        } else if (deal.getToCurrency().length() != 3) {
            errors.rejectValue("toCurrency", "Length", "To Currency ISO Code must be exactly 3 letters.");
        }

        if (deal.getDealTimestamp() == null) {
            errors.rejectValue("dealTimestamp", "NotNull", "Deal Timestamp is missing.");
        }

        if (deal.getDealAmount() == null) {
            errors.rejectValue("dealAmount", "NotNull", "Deal Amount is missing.");
        } else if (deal.getDealAmount().compareTo(BigDecimal.ZERO) <= 0) {
            errors.rejectValue("dealAmount", "Positive", "Deal Amount should be a positive number.");
        }
    }

}
