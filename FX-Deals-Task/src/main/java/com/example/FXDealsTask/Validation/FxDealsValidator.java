package com.example.FXDealsTask.Validation;

import com.example.FXDealsTask.model.FxDeals;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

@Component
public class FxDealsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return FxDeals.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        FxDeals deal = (FxDeals) target;

        if (deal.getDealUniqueId() == null || deal.getDealUniqueId().isEmpty()) {
            errors.rejectValue("dealUniqueId", "NotNull", "Deal Unique Id is missing.");
        }

        if (deal.getFromCurrency() == null || deal.getFromCurrency().isEmpty()) {
            errors.rejectValue("fromCurrencyIsoCode", "NotNull", "From Currency ISO Code is missing.");
        }

        if (deal.getToCurrency() == null || deal.getToCurrency().isEmpty()) {
            errors.rejectValue("toCurrencyIsoCode", "NotNull", "To Currency ISO Code is missing.");
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
