package com.budgetdebt.recorder.budget_and_debt_recorder.model.category;

import java.time.LocalDateTime;
// import java.util.List;

// import com.budgetdebt.recorder.budget_and_debt_recorder.model.records.Records;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tbl_categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Getter
    @Column(name = "user_id")
    private int userId;
    
    @Setter
    @Getter
    @Column(name = "parent_category")
    private int parentCategory;

    @Setter
    @Getter
    @Column(name = "category_name")
    private String categoryName;

    @Setter
    @Getter
    @Column(name = "category_type")
    private String categoryType;

    @Setter
    @Getter
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Setter
    @Getter
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    @Setter
    @Getter
    @Column(name = "active_status")
    private char activeStatus = '1';
    
    // @Getter
    // @OneToMany(mappedBy = "category")
    // private List<Records> records;

    public Category() {

    }
}
