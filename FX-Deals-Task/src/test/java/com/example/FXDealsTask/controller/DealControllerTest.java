package com.example.FXDealsTask.controller;

import com.example.FXDealsTask.exceptions.SameCurrencyException;
import com.example.FXDealsTask.validation.ValidationService;
import com.example.FXDealsTask.model.FxDeal;
import com.example.FXDealsTask.service.DealServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DealControllerTest {

    @Mock
    private ValidationService validationService;

    @Mock
    private DealServiceImpl dealService;

    @InjectMocks
    private DealController dealController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createDeal_ValidDeal_ShouldReturnOk() {
        int id = 1;
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        Timestamp dealTimestamp = Timestamp.valueOf("2024-06-09 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(1000.50);
        FxDeal deal = new FxDeal(id, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        when(validationService.validateFxDeal(deal)).thenReturn(new BeanPropertyBindingResult(deal, "fxDeals"));

        when(dealService.save(deal)).thenReturn(deal);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Deal processed successfully", response.getBody());
    }


    @Test
    public void createDeal_InvalidFromCurrency_ShouldReturnValidationError() {
        int id = 1;
        String fromCurrency = "US";
        String toCurrency = "EUR";
        Timestamp dealTimestamp = Timestamp.valueOf("2024-06-09 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(1000.50);
        FxDeal deal = new FxDeal(id, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        BindingResult bindingResult = new BeanPropertyBindingResult(deal, "fxDeal");
        bindingResult.rejectValue("fromCurrency", "Length", "From Currency ISO Code must be exactly 3 letters.");

        when(validationService.validateFxDeal(deal)).thenReturn(bindingResult);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("From Currency ISO Code must be exactly 3 letters."));
    }

    @Test
    public void createDeal_InvalidToCurrency_ShouldReturnValidationError() {
        int id = 1;
        String fromCurrency = "USD";
        String toCurrency = "EU";
        Timestamp dealTimestamp = Timestamp.valueOf("2024-06-09 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(1000.50);
        FxDeal deal = new FxDeal(id, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        BindingResult bindingResult = new BeanPropertyBindingResult(deal, "fxDeal");
        bindingResult.rejectValue("toCurrency", "Length", "To Currency ISO Code must be exactly 3 letters.");

        when(validationService.validateFxDeal(deal)).thenReturn(bindingResult);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("To Currency ISO Code must be exactly 3 letters."));
    }


    @Test
    public void createDeal_InvalidDeal_ShouldReturn402() {
        int id = 1;
        String fromCurrency = "USD";
        String toCurrency = "USD";
        Timestamp dealTimestamp = Timestamp.valueOf("2024-05-17 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(1000.50);

        FxDeal deal = new FxDeal(id, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        when(validationService.validateFxDeal(deal)).thenReturn(new BeanPropertyBindingResult(deal, "fxDeals"));

        doThrow(new SameCurrencyException("Deals can't be the same currency")).when(dealService).save(deal);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().contains("Deals can't be the same currency"));
    }

    @Test
    public void createDeal_MissingFromCurrency_ShouldReturnValidationError() {
        int id = 1;
        String fromCurrency = null;
        String toCurrency = "EUR";
        Timestamp dealTimestamp = Timestamp.valueOf("2024-06-09 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(1000.50);
        FxDeal deal = new FxDeal(id, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        BindingResult bindingResult = new BeanPropertyBindingResult(deal, "fxDeal");
        bindingResult.rejectValue("fromCurrency", "NotNull", "From Currency ISO Code is missing.");

        when(validationService.validateFxDeal(deal)).thenReturn(bindingResult);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("From Currency ISO Code is missing."));
    }

    @Test
    public void createDeal_MissingToCurrency_ShouldReturnValidationError() {
        int id = 1;
        String fromCurrency = "USD";
        String toCurrency = null;
        Timestamp dealTimestamp = Timestamp.valueOf("2024-06-09 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(1000.50);
        FxDeal deal = new FxDeal(id, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        BindingResult bindingResult = new BeanPropertyBindingResult(deal, "fxDeal");
        bindingResult.rejectValue("toCurrency", "NotNull", "To Currency ISO Code is missing.");

        when(validationService.validateFxDeal(deal)).thenReturn(bindingResult);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("To Currency ISO Code is missing."));
    }


    @Test
    public void createDeal_InvalidDealAmount_ShouldReturnValidationError() {
        int id = 1;
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        Timestamp dealTimestamp = Timestamp.valueOf("2024-06-09 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(-1000.50);
        FxDeal deal = new FxDeal(id, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        BindingResult bindingResult = new BeanPropertyBindingResult(deal, "fxDeal");
        bindingResult.rejectValue("dealAmount", "Positive", "Deal amount must be positive.");

        when(validationService.validateFxDeal(deal)).thenReturn(bindingResult);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Deal amount must be positive."));
    }


    @Test
    public void createDeal_NullDeal_ShouldReturn400() {
        ResponseEntity<String> response = dealController.createDeal(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
