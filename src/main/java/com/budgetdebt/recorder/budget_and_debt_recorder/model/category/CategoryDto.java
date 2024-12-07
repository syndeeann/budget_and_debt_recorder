package com.budgetdebt.recorder.budget_and_debt_recorder.model.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class CategoryDto {
   
    @Setter
    @Getter
    private Integer user_id;
    
    @Setter
    @Getter
    private Integer parent_category = null;

    @NotEmpty
    @Setter
    @Getter
    private String category_name;

    @NotEmpty
    @Setter
    @Getter
    private String category_type;
}
