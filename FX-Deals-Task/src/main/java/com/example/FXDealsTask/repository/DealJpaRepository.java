package com.example.FXDealsTask.repository;

import com.example.FXDealsTask.model.FxDeals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealJpaRepository extends JpaRepository<FxDeals, String> {

}

