package com.budgetdebt.recorder.budget_and_debt_recorder.model.auth;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class RegisterDto {
    @NotEmpty
    @Getter
    @Setter
    private String fullname;

    @Getter
    @Setter
    private String role;

    @NotEmpty
    @Getter
    @Setter
    private String username;

    @NotEmpty
    @Getter
    @Setter
    @Size(min = 6, message = "Minimum Password length is 6 characters")
    private String password;
}
