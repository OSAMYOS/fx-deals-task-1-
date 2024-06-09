package com.example.FXDealsTask.validation;

import com.example.FXDealsTask.model.FxDeal;
import org.springframework.validation.Errors;

public interface ValidationService {
    Errors validateFxDeal(FxDeal fxDeal);
}
