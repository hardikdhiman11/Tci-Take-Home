package com.example.TakeHomeAssignment.service;

import com.example.TakeHomeAssignment.dto.request.EmployeeRequest;
import com.example.TakeHomeAssignment.dto.response.GetEmployeeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeeService {
    boolean saveEmployeeList(List<EmployeeRequest> employees);
    GetEmployeeResponse getEmployeeBonusByDate(String date);
}
