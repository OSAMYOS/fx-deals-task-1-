package com.example.FXDealsTask.Validation;

import com.example.FXDealsTask.model.FxDeals;
import org.springframework.validation.Errors;

import java.util.List;


public interface ValidationService {
    Errors validateFxDeal(FxDeals fxDeal);
}
