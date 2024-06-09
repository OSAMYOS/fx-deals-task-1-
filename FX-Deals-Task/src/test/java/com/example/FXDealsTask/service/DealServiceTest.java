package com.example.FXDealsTask.service;

import com.example.FXDealsTask.exceptions.CurrencyNotFoundException;
import com.example.FXDealsTask.exceptions.DealNotFoundException;
import com.example.FXDealsTask.exceptions.DuplicateDealException;
import com.example.FXDealsTask.model.FxDeal;
import com.example.FXDealsTask.repository.DealJpaRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DealServiceTest {

    @Mock
    private DealJpaRepository repository;

    @InjectMocks
    private DealServiceImpl dealService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void save_ValidDeal_ShouldSaveSuccessfully() {
        Timestamp fixedTimestamp = Timestamp.valueOf("2024-06-09 20:01:21.859");

        FxDeal deal = new FxDeal("USD", "EUR", fixedTimestamp, BigDecimal.valueOf(1000.50));
        FxDeal expectedDeal = new FxDeal("USD", "EUR", fixedTimestamp, BigDecimal.valueOf(1000.50));

        when(repository.save(any(FxDeal.class))).thenReturn(expectedDeal);

        FxDeal actualDeal = dealService.save(deal);

        assertEquals(expectedDeal, actualDeal);
        verify(repository, times(1)).save(deal);
    }

    @Test
    public void testGetDealByIdWhenNotFound() {

        when(repository.findById(1)).thenThrow(DealNotFoundException.class);

        assertThrows(DealNotFoundException.class, () -> {
            dealService.findById(1);
        });
        verify(repository, times(1)).findById(1);
    }

    @Test
    public void save_DuplicateDeal_ShouldThrowDuplicateDealException() {

        FxDeal deal = new FxDeal("USD", "EUR", Timestamp.valueOf("2024-06-09 20:01:21.859"), BigDecimal.valueOf(1000.50));
        when(repository.findById(deal.getId())).thenReturn(Optional.of(deal));

        assertThrows(DuplicateDealException.class, () -> {
            dealService.save(deal);
        });
        verify(repository, never()).save(any(FxDeal.class));
    }

    @Test
    public void findById_InvalidId_ShouldThrowDealNotFoundException() {
        int id = 999;

        when(repository.findById(String.valueOf(id))).thenReturn(Optional.empty());

        assertThrows(DealNotFoundException.class, () -> {
            dealService.findById(id);
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_NullDeal_ShouldThrowException() {
        dealService.save(null);
    }
}
