package com.budgetdebt.recorder.budget_and_debt_recorder.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.budgetdebt.recorder.budget_and_debt_recorder.repositories.RecordsRepository;
import com.budgetdebt.recorder.budget_and_debt_recorder.model.records.Records;
import com.budgetdebt.recorder.budget_and_debt_recorder.model.records.RecordsDto;

@Service
public class RecordsService {

    @Autowired
    private RecordsRepository recordsRepository;

    
    public ResponseEntity<Object> getAllActiveRecords() {
        var response = new HashMap<String, Object>();
        char status = '1';
        List<Records> records = recordsRepository.findByActiveStatus(status);

        response.put("records", records);

        return ResponseEntity.ok(response);
    }


    public ResponseEntity<Object> saveRecord(RecordsDto recordDto, BindingResult result) {
        if(result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorList.size(); i++){
                var error = (FieldError) errorList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }

        Records record = new Records();
        record.setAmount(recordDto.getAmount());
        record.setCategoryId(recordDto.getCategory_id());
        record.setNote(recordDto.getNote());
        record.setDateCreated(LocalDateTime.now());
        
        try {
                Records savedRecords = recordsRepository.save(record);

                var response = new HashMap<String, Object>();
                response.put("record", savedRecords);

                return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("There is an Exception: ");
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Error");
    }

    public ResponseEntity<Object> updateRecord(Integer id, RecordsDto recordDto, BindingResult result) {
        if(result.hasErrors()) {
            var errorList = result.getAllErrors();
            var errorsMap = new HashMap<String, String>();

            for(int i = 0; i < errorList.size(); i++){
                var error = (FieldError) errorList.get(i);
                errorsMap.put(error.getField(), error.getDefaultMessage());
            }

            return ResponseEntity.badRequest().body(errorsMap);
        }

        Optional<Records> optionalRecords = recordsRepository.findById(id);

        Records record = optionalRecords.get();
        record.setAmount(recordDto.getAmount());
        record.setCategoryId(recordDto.getCategory_id());
        record.setNote(recordDto.getNote());
        record.setDateUpdated(LocalDateTime.now());
        
        try {
                Records savedRecords = recordsRepository.save(record);

                var response = new HashMap<String, Object>();
                response.put("record", savedRecords);

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
        Optional<Records> optionalRecords = recordsRepository.findById(id);
        if(optionalRecords.isEmpty()){
            return ResponseEntity.badRequest().body("Records does not exist.");
        }

        Records records = optionalRecords.get();
        records.setActiveStatus(status);
        records.setDateUpdated(LocalDateTime.now());
        
        try {
            Records savedRecords = recordsRepository.save(records);

                var response = new HashMap<String, Object>();
                response.put("records", savedRecords);

                return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.out.println("There is an Exception: ");
            ex.printStackTrace();
        }
        return ResponseEntity.badRequest().body("Error");
    }
}
