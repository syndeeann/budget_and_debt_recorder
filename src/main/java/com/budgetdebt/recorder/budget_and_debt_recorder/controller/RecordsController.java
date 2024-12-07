package com.budgetdebt.recorder.budget_and_debt_recorder.controller;

// import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.budgetdebt.recorder.budget_and_debt_recorder.model.records.RecordsDto;
import com.budgetdebt.recorder.budget_and_debt_recorder.service.RecordsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/records")
public class RecordsController {

    @Autowired
    private RecordsService recordsService;

    @GetMapping("/")
    public ResponseEntity<Object> listActiveRecords() {
        return recordsService.getAllActiveRecords();
    }
    
    @PostMapping("/")
    public ResponseEntity<Object> save(@Valid @RequestBody RecordsDto recordsDto, BindingResult result) {
        return recordsService.saveRecord(recordsDto, result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @Valid @RequestBody RecordsDto recordsDto, BindingResult result){
        return recordsService.updateRecord(id, recordsDto, result);
    }

    @PutMapping("/{id}/deactivate")
    public ResponseEntity<Object> deactivate(@PathVariable Integer id) {
        char status = '0';
        return recordsService.updateStatus(id, status);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Object> activate(@PathVariable Integer id) {
        char status = '1';
        return recordsService.updateStatus(id, status);
    }
}
