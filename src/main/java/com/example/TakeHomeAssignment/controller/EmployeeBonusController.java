package com.example.TakeHomeAssignment.controller;

import com.example.TakeHomeAssignment.dto.request.EmployeeListRequest;
import com.example.TakeHomeAssignment.dto.response.GetEmployeeResponse;
import com.example.TakeHomeAssignment.service.EmployeeService;
import com.example.TakeHomeAssignment.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tci")
public class EmployeeBonusController {

    private final EmployeeService employeeService;

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
    public ResponseEntity<GetEmployeeResponse> getGroupedEmployees(@RequestParam("date") String date){
        return ResponseEntity.ok().body(employeeService.getEmployeeBonusByDate(date));
    }

}
