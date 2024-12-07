package com.budgetdebt.recorder.budget_and_debt_recorder.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.category.Category;
import com.budgetdebt.recorder.budget_and_debt_recorder.model.category.CategoryDto;
import com.budgetdebt.recorder.budget_and_debt_recorder.repositories.CategoryRepository;
import com.budgetdebt.recorder.budget_and_debt_recorder.repositories.UserRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;


    /** 
     * Method that fetch all active category
     * */
    public ResponseEntity<Object> getAllActiveCategories(Authentication auth) {
        var response = new HashMap<String, Object>();
        var user = userRepository.findByUsername(auth.getName());
        List<Category> categories = categoryRepository.findByUserIdAndActiveStatus(user.getId(), '1');
               
        
        response.put("categories", categories);

        return ResponseEntity.ok(response);
    }

    /** 
     * Method that adds a category field
     * Checks if similar data is existing before saving
     * */
    public ResponseEntity<Object> saveCategory(CategoryDto categoryDto, BindingResult result, Authentication auth) {
        if(result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorList.size(); i++){
                var error = (FieldError) errorList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }
        //get user id
        var user = userRepository.findByUsername(auth.getName());

        Category category = new Category();
        category.setUserId(user.getId());
        category.setParentCategory(categoryDto.getParent_category());
        category.setCategoryName(categoryDto.getCategory_name());
        category.setCategoryType(categoryDto.getCategory_type());
        category.setDateCreated(LocalDateTime.now());
        
        try {
            var existingCategory = categoryRepository.findByUserIdAndCategoryNameAndParentCategoryAndCategoryType(
                user.getId(), categoryDto.getCategory_name(), categoryDto.getParent_category(), categoryDto.getCategory_type());

                if(existingCategory != null) {
                    return ResponseEntity.badRequest().body("This category already exists.");
                }

                categoryRepository.save(category);

                var response = new HashMap<String, Object>();
                response.put("category", category);

                return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("There is an Exception: ");
            ex.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Error");
    }

    /** 
     * Method that updates a category field
     * Checks if similar data is existing before saving
     * */
    public ResponseEntity<Object> updateCategory(Integer id, CategoryDto categoryDto, BindingResult result) {
        if(result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorList.size(); i++){
                var error = (FieldError) errorList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return ResponseEntity.badRequest().body("Category does not exist.");
        }

        Category category = optionalCategory.get();
        category.setParentCategory(categoryDto.getParent_category());
        category.setCategoryName(categoryDto.getCategory_name());
        category.setCategoryType(categoryDto.getCategory_type());
        category.setDateUpdated(LocalDateTime.now());
        
        try {
            var existingCategory = categoryRepository.findByUserIdAndCategoryNameAndParentCategoryAndCategoryType(
                category.getUserId(), categoryDto.getCategory_name(), categoryDto.getParent_category(), categoryDto.getCategory_type());

                if(existingCategory != null) {
                    return ResponseEntity.badRequest().body("This category already exists.");
                }

                categoryRepository.save(category);

                var response = new HashMap<String, Object>();
                response.put("category", category);

                return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("There is an Exception: ");
            ex.printStackTrace();
        }

        return ResponseEntity.badRequest().body("Error");
    }

    /** 
     * Method that updates a category's status
     * */
    public ResponseEntity<Object> updateStatus(Integer id, char status) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            return ResponseEntity.badRequest().body("Category does not exist.");
        }

        Category category = optionalCategory.get();
        category.setActiveStatus(status);
        category.setDateUpdated(LocalDateTime.now());
        
        try {
                Category savedCategory = categoryRepository.save(category);

                var response = new HashMap<String, Object>();
                response.put("category", savedCategory);

                return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("There is an Exception: ");
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Error");
    }
}
