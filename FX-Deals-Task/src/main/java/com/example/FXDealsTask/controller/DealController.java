package com.example.FXDealsTask.controller;

import com.example.FXDealsTask.Exceptions.CurrencyNotFoundException;
import com.example.FXDealsTask.Exceptions.SameCurrencyException;
import com.example.FXDealsTask.Validation.ValidationService;
import com.example.FXDealsTask.model.FxDeal;
import com.example.FXDealsTask.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/findAll")
    public ResponseEntity<List<FxDeal>> findAll() {
        List<FxDeal> fxDeals = dealService.findAll();
        return ResponseEntity.ok(fxDeals);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<FxDeal> findById(@PathVariable int id) {
        FxDeal fxDeal = dealService.findById(id).orElseThrow(() -> new CurrencyNotFoundException("Deal not found with id: " + id));
        return ResponseEntity.ok(fxDeal);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createDeal(@RequestBody FxDeal deal) {
        if (deal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deal cannot be null");
        }

        BindingResult validationResult = (BindingResult) validationService.validateFxDeal(deal);

        if (validationResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed: ");
            for (FieldError error : validationResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }

        try {
            dealService.save(deal);
            return ResponseEntity.status(HttpStatus.CREATED).body("Deal processed successfully");
        } catch (SameCurrencyException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
