package com.example.TakeHomeAssignment.service.impl;

import com.example.TakeHomeAssignment.dto.request.EmployeeRequest;
import com.example.TakeHomeAssignment.dto.response.EmployeeBonusResponse;
import com.example.TakeHomeAssignment.dto.response.GetEmployeeResponse;
import com.example.TakeHomeAssignment.model.Department;
import com.example.TakeHomeAssignment.model.Employee;
import com.example.TakeHomeAssignment.repository.DepartmentRepo;
import com.example.TakeHomeAssignment.repository.EmployeeRepo;
import com.example.TakeHomeAssignment.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class EmployeeBonusServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final DepartmentRepo departmentRepo;

    private static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("MMM-dd-yyyy")
            .toFormatter(Locale.ENGLISH);;


    @Override
    public boolean saveEmployeeList(List<EmployeeRequest> dtoRequests) {
        //Step1: Convert the list of dto request to stream.
        //Step2: Map the dto request to employee object and save department and employee
        //       in their respective tables.
        //Step2: Save all employees.
        try {
            List<Employee> employees = dtoRequests.stream()
                    .map(dto -> convertRequestToEmployee(dto))
                    .collect(Collectors.toList());
            employeeRepo.saveAll(employees);
        }catch (Exception e){
            return false;
        }
        return true;
    }


    private Employee convertRequestToEmployee(EmployeeRequest request){
        //Step1: Check if department already exists by name in repository.
        //Step2: If department is present save the employee with id of existing department in the table
        //       or create a new department and save the id of that department in the employee table.
        //Step3: Save employee with its department
        Optional<Department> departmentOptional = departmentRepo.findByDepartmentName(request.getDepartment());
        Department department;
        if (departmentOptional.isPresent()){
            department = departmentOptional.get();
        }
        else{
            department = Department.builder()
                    .departmentName(request.getDepartment())
                    .build();
            departmentRepo.save(department);
        }
        Employee employee = Employee.builder()
                .empName(request.getEmpName())
                .department(department)
                .amount(request.getAmount())
                .currency(request.getCurrency())
                .joiningDate( LocalDate.parse(request.getJoiningDate(),DATE_FORMATTER) )
                .build();

        Optional<String> exitDate =  Optional.ofNullable(request.getExitDate());
        exitDate.ifPresentOrElse(date ->employee.setExitDate( LocalDate.parse(date,DATE_FORMATTER)),
                ()->employee.setExitDate(null));
        return employee;
    }

    @Override
    public GetEmployeeResponse getEmployeeBonusByDate(String date) {

        //Step1: Get eligible employees list using jpql query.
        //Step2: Group employees by currency and then map the employee to the EmployeeBonusResponse class and
        //downstream it to be collected into a list.
        //Step3: Then return the desired response Wrapper object
        String trimmedDate = date.replace("\"","");
        LocalDate localDate = LocalDate.parse(trimmedDate,DATE_FORMATTER);
        log.info("Local Date {}",trimmedDate);
        log.info("List of employees {}",employeeRepo.findEligibleEmployees(localDate));
        Map<String,List<EmployeeBonusResponse>> groupedEmployees = employeeRepo.findEligibleEmployees(localDate)
                .stream()
                .collect(Collectors.groupingBy( Employee::getCurrency ,
                        Collectors.mapping( emp-> {
                            EmployeeBonusResponse response = EmployeeBonusResponse.builder()
                                    .empName(emp.getEmpName())
                                    .amount(emp.getAmount())
                                    .build();
                            return response;
                        },Collectors.toList() )));
        GetEmployeeResponse response = GetEmployeeResponse.builder()
                .errorMessage("")
                .data(groupedEmployees)
                .build();
        return response;
    }
}
