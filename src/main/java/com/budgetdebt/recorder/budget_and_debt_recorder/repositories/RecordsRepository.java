package com.budgetdebt.recorder.budget_and_debt_recorder.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.records.Records;

public interface RecordsRepository extends JpaRepository<Records, Integer>{
    public List<Records> findByActiveStatus(char status);
}
