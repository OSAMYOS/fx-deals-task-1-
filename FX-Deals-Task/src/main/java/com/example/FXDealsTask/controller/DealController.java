package com.example.FXDealsTask.controller;

import com.example.FXDealsTask.Exceptions.CurrencyNotFoundException;
import com.example.FXDealsTask.Exceptions.SameCurrencyException;
import com.example.FXDealsTask.Validation.ValidationService;
import com.example.FXDealsTask.model.FxDeals;
import com.example.FXDealsTask.service.DealService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deals")
public class DealController {

    private final DealService dealService;
    private final ValidationService validationService;

    @Autowired
    public DealController(DealService dealService, ValidationService validationService) {
        this.dealService = dealService;
        this.validationService = validationService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDeal(@RequestBody FxDeals deal) {
        BindingResult validationResult = (BindingResult) validationService.validateFxDeal(deal);

        // If there are validation errors, return 402 Payment Required status code
        if (validationResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed: ");
            for (FieldError error : validationResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(errorMessage.toString());
        }

        // Save the deal if validation passes
        try {
            dealService.save(deal);
            return ResponseEntity.ok("Deal processed successfully");
        } catch (CurrencyNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (SameCurrencyException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
