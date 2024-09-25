package com.example.TakeHomeAssignment.controller;

import com.example.TakeHomeAssignment.dto.request.EmployeeListRequest;
import com.example.TakeHomeAssignment.dto.response.GetEmployeeResponse;
import com.example.TakeHomeAssignment.service.EmployeeService;
import com.example.TakeHomeAssignment.utils.MessageUtils;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequiredArgsConstructor
@RequestMapping("/tci")
@Validated
public class EmployeeBonusController {

    private final EmployeeService employeeService;
    private final Validator validator;

    @PostMapping("/employee-bonus")
    public ResponseEntity<String> saveEmployeeBonusData(@Valid @RequestBody EmployeeListRequest request){

        if (request.getEmployees().isEmpty()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(400))
                    .body(MessageUtils.EMPTY_LIST);
        }
        boolean result = employeeService.saveEmployeeList(request.getEmployees());
        if (result==true){
            return ResponseEntity.ok().body(MessageUtils.SAVED_SUCCESSFULLY);
        }
        return ResponseEntity.status(HttpStatusCode.valueOf(400))
                .body(MessageUtils.NOT_SAVED);
    }


    @GetMapping("//employee-bonus")
    public ResponseEntity<GetEmployeeResponse> getGroupedEmployees(@RequestParam("date")
                                                                       @Pattern(regexp = "^\"(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)-([0-2][0-9]|3[01])-(19|20)\\d{2}\"$",
                                                                               message = "Joining date format is not valid")
                                                                    String date){
        return ResponseEntity.ok().body(employeeService.getEmployeeBonusByDate(date));
    }

}
