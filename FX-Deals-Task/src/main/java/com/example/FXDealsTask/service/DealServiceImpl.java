package com.example.FXDealsTask.service;

import com.example.FXDealsTask.Exceptions.CurrencyNotFoundException;
import com.example.FXDealsTask.Exceptions.GlobalExceptionHandler;
import com.example.FXDealsTask.Exceptions.SameCurrencyException;
import com.example.FXDealsTask.model.FxDeals;
import com.example.FXDealsTask.repository.DealJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DealServiceImpl implements DealService {

    private static final Logger logger = LoggerFactory.getLogger(DealServiceImpl.class);
    private final DealJpaRepository repository;

    @Autowired
    public DealServiceImpl(DealJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FxDeals> findAll() {
        logger.info("Fetching all deals");
        return List.of((FxDeals) repository.findAll());
    }

    @Override
    public Optional<FxDeals> findById(int theId) {
        logger.info("Fetching deal with id: {}", theId);
        if (theId <= 0) {
            logger.error("Invalid deal id: {}", theId);
            throw new CurrencyNotFoundException("Invalid deal id: " + theId);
        }

        Optional<FxDeals> deal=repository.findById(String.valueOf(theId));
        if (deal.isEmpty()) {
            logger.error("deal not found: {}", theId);
            throw new CurrencyNotFoundException("deal not found: " + theId);
        }
        return deal;
    }

    @Override
    public void save(FxDeals theDeal) {
        logger.info("Saving deal: {}", theDeal);
        if (theDeal == null) {
            logger.error("Deal cannot be null");
            throw new IllegalArgumentException("Deal cannot be null");
        }
        if (theDeal.getToCurrency().equals(theDeal.getFromCurrency())){
            logger.error("Deals can't be the same currency");
            throw new SameCurrencyException("Deals can't be the same currency");
        }
        Optional<FxDeals> deal=findById(Integer.parseInt(theDeal.getDealUniqueId()));
        if (deal.isPresent()) {
            repository.save(theDeal);
            logger.info("Deal saved: {}", theDeal);
        }
        logger.error("deal not found: {}", theDeal.getDealUniqueId());
        throw new CurrencyNotFoundException("deal not found: "+theDeal.getDealUniqueId());
    }

}
