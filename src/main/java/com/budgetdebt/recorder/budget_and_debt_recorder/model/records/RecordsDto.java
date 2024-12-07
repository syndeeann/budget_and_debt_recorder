package com.budgetdebt.recorder.budget_and_debt_recorder.model.records;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class RecordsDto {
    @Getter
    @Setter
    private Double amount = 0.00;

    @Getter
    @Setter
    private String note;

    @NotNull
    @Getter
    @Setter
    private Integer category_id;
}
