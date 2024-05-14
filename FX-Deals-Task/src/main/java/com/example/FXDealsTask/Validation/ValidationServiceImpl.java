package com.example.FXDealsTask.Validation;

import com.example.FXDealsTask.model.FxDeals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;


@Service
public class ValidationServiceImpl implements ValidationService {
    private final FxDealsValidator fxDealsValidator;

    @Autowired
    public ValidationServiceImpl(FxDealsValidator fxDealsValidator) {
        this.fxDealsValidator = fxDealsValidator;
    }

    @Override
    public Errors validateFxDeal(FxDeals deal) {
        Errors errors = new BeanPropertyBindingResult(deal, "fxDeals");
        fxDealsValidator.validate(deal,  errors);
        return errors;
    }
}