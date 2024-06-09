package com.example.FXDealsTask.service;

import com.example.FXDealsTask.model.FxDeal;

import java.util.List;
import java.util.Optional;

public interface DealService {

    List<FxDeal> findAll();

    Optional<FxDeal> findById(int theId);

    FxDeal save(FxDeal theDeal);

}
