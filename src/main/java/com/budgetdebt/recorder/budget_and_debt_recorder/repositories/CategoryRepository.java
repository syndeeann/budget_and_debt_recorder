package com.budgetdebt.recorder.budget_and_debt_recorder.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.category.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer>{
    public Category findByUserIdAndCategoryNameAndParentCategoryAndCategoryType(int user_id, String category_name, int parent_category, String category_type);

    public List<Category> findByUserIdAndActiveStatus(Integer user_id, char active_status);
}
