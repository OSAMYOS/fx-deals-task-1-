package com.example.FXDealsTask.repository;

import com.example.FXDealsTask.model.FxDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DealJpaRepository extends JpaRepository<FxDeal, String> {
    Optional<FxDeal> findById(int dealUniqueId);
}

