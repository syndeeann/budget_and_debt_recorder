package com.budgetdebt.recorder.budget_and_debt_recorder.model.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class LoginDto {
    @NotEmpty
    @Getter
    @Setter
    private String username;

    @NotEmpty
    @Getter
    @Setter
    private String password;
}
