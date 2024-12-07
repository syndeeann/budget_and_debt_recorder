package com.budgetdebt.recorder.budget_and_debt_recorder.model.records;

import java.time.LocalDateTime;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.category.Category;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tbl_records")
public class Records {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Getter
    @Setter
    private Double amount;

    @Getter
    @Setter
    private String note;

    @Getter
    @Setter
    @Column(name="category_id")
    private Integer categoryId;

    @Getter
    @Setter
    @Column(name="date_created")
    private LocalDateTime dateCreated;

    @Getter
    @Setter
    @Column(name="date_updated")
    private LocalDateTime dateUpdated;

    @Getter
    @Setter
    @Column(name="active_status")
    private char activeStatus = '1';

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="category_id", table="tbl_records", insertable = false, updatable = false)
    private Category category;
}
