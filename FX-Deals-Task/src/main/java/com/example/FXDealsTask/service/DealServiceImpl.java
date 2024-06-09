package com.example.FXDealsTask.service;

import com.example.FXDealsTask.Exceptions.CurrencyNotFoundException;
import com.example.FXDealsTask.Exceptions.DealNotFoundException;
import com.example.FXDealsTask.Exceptions.DuplicateDealException;
import com.example.FXDealsTask.Exceptions.SameCurrencyException;
import com.example.FXDealsTask.model.FxDeal;
import com.example.FXDealsTask.repository.DealJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DealServiceImpl implements DealService {

    private final DealJpaRepository repository;

    @Autowired
    public DealServiceImpl(DealJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FxDeal> findAll() {
        log.info("Fetching all deals");
        if (repository.count() == 0) {
            log.error("No deals found");
            throw new CurrencyNotFoundException("No deals found");
        }
        return repository.findAll();
    }

    @Override
    public Optional<FxDeal> findById(int theId) {
        log.info("Fetching deal with id: {}", theId);
        if (theId <= 0) {
            log.error("Invalid deal id: {}", theId);
            throw new DealNotFoundException("Invalid deal id: " + theId);
        }

        Optional<FxDeal> deal=repository.findById(theId);
        if (deal.isEmpty()) {
            log.error("deal not found: {}", theId);
            throw new DealNotFoundException("deal not found: " + theId);
        }
        return deal;
    }

    @Override
    @Transactional
    public FxDeal save(FxDeal theDeal) {
        log.info("Trying to save deal: {}", theDeal);
        if (theDeal == null) {
            log.error("Deal cannot be null");
            throw new IllegalArgumentException("Deal cannot be null");
        }

        if (theDeal.getToCurrency().equals(theDeal.getFromCurrency())){
            log.error("Deals can't be the same currency");
            throw new SameCurrencyException("Deals can't be the same currency");
        }

        Optional<FxDeal> existingDeal = repository.findById(theDeal.getId());
        if (existingDeal.isPresent()) {
            log.error("Deal already exists: {}", theDeal.getId());
            throw new DuplicateDealException("Deal already exists: " + theDeal.getId());
        }

        repository.save(theDeal);
        log.info("Deal saved: {}", theDeal);

        return theDeal;
    }



}
