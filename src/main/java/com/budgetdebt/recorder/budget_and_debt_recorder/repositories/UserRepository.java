package com.budgetdebt.recorder.budget_and_debt_recorder.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.users.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    // You can define custom queries here if needed, e.g., findByName, etc.

    public Users findByUsername(String username);
}
