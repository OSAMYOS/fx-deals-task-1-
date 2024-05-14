package com.example.FXDealsTask.service;

import com.example.FXDealsTask.Exceptions.CurrencyNotFoundException;
import com.example.FXDealsTask.model.FxDeals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DealServiceTest {

    @Mock
    private DealServiceImpl repository;

    @InjectMocks
    private DealServiceImpl dealService;

    @Test
    public void save_ValidDeal_ShouldSaveSuccessfully() {
        FxDeals deal = new FxDeals();
        deal.setDealUniqueId("1");

        when(repository.findById(1)).thenReturn(Optional.empty());

        dealService.save(deal);

        //verify(repository, times(1)).save(deal);
    }

    @Test(expected = CurrencyNotFoundException.class)
    public void save_DealNotFound_ShouldThrowException() {
        FxDeals deal = new FxDeals();
        deal.setDealUniqueId("1");

        when(repository.findById(1)).thenReturn(Optional.empty());

        dealService.save(deal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void save_NullDeal_ShouldThrowException() {
        dealService.save(null);
    }
}
