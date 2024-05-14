package com.example.FXDealsTask.controller;

import com.example.FXDealsTask.Exceptions.CurrencyNotFoundException;
import com.example.FXDealsTask.Validation.ValidationService;
import com.example.FXDealsTask.model.FxDeals;
import com.example.FXDealsTask.service.DealServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;

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

    @Test
    public void createDeal_ValidDeal_ShouldReturnOk() {
        String dealUniqueId = "123456";
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        Timestamp dealTimestamp = Timestamp.valueOf("2024-05-17 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(1000.50);

        FxDeals deal = new FxDeals(dealUniqueId, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        when(validationService.validateFxDeal(deal)).thenReturn(new BeanPropertyBindingResult(deal, "fxDeals"));
        doThrow(new CurrencyNotFoundException("Deal not found")).when(dealService).save(deal);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deal processed successfully", response.getBody());
    }

    @Test
    public void createDeal_InvalidDeal_ShouldReturn402() {
        String dealUniqueId = "123456";
        String fromCurrency = "USD";
        String toCurrency = "EUR";
        Timestamp dealTimestamp = Timestamp.valueOf("2024-05-17 10:30:00");
        BigDecimal dealAmount = BigDecimal.valueOf(1000.50);

        FxDeals deal = new FxDeals(dealUniqueId, fromCurrency, toCurrency, dealTimestamp, dealAmount);

        when(validationService.validateFxDeal(deal)).thenReturn(new BeanPropertyBindingResult(deal, "fxDeals"));
        doReturn(ResponseEntity.ok("Deal processed successfully")).when(dealService).save(deal);

        ResponseEntity<String> response = dealController.createDeal(deal);

        assertEquals(HttpStatus.PAYMENT_REQUIRED, response.getStatusCode());
        assertTrue(response.getBody().contains("Deal not found"));
    }

    @Test
    public void createDeal_NullDeal_ShouldReturn400() {
        ResponseEntity<String> response = dealController.createDeal(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}
