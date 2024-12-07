package com.budgetdebt.recorder.budget_and_debt_recorder.model.users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.time.LocalDateTime;
import lombok.Setter;
import lombok.Getter;

@Entity
@Table(name="tbl_user")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;

    @Getter
    @Setter
    private String fullname;

    @Getter
    @Setter
    private LocalDateTime date_created;

    @Getter
    @Setter
    private LocalDateTime date_updated;

    @Getter
    @Setter
    private String role;

    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    @Setter
    @Getter
    private char active_status = '1';

    public Users() {
        
    }
}
