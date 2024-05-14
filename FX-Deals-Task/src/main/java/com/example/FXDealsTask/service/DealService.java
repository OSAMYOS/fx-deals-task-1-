package com.example.FXDealsTask.service;

import com.example.FXDealsTask.model.FxDeals;

import java.util.List;
import java.util.Optional;

public interface DealService {

    List<FxDeals> findAll();

    Optional<FxDeals> findById(int theId);

    void save(FxDeals theDeal);

}
