package com.example.FXDealsTask.Validation;

import com.example.FXDealsTask.model.FxDeal;
import org.springframework.validation.Errors;

public interface ValidationService {
    Errors validateFxDeal(FxDeal fxDeal);
}
