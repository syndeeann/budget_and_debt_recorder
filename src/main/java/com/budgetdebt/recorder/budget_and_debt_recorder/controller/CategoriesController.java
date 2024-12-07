package com.budgetdebt.recorder.budget_and_debt_recorder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.category.CategoryDto;
import com.budgetdebt.recorder.budget_and_debt_recorder.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<Object> listCategories(Authentication auth) {
        return categoryService.getAllActiveCategories(auth);
    }

    @PostMapping("/add")
    public ResponseEntity<Object> save(@Valid @RequestBody CategoryDto categoryDto, BindingResult result, Authentication auth) {
        return categoryService.saveCategory(categoryDto, result, auth);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @Valid @RequestBody CategoryDto categoryDto, BindingResult result) {
        return categoryService.updateCategory(id, categoryDto, result);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Object> deactivate(@PathVariable Integer id) {
        char status = '0';
        return categoryService.updateStatus(id, status);
    }
    
    @PutMapping("/{id}/activate")
    public ResponseEntity<Object> activate(@PathVariable Integer id) {
        char status = '1';
        return categoryService.updateStatus(id, status);
    }
}
