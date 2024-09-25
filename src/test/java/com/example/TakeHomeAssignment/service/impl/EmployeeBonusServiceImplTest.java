package com.example.TakeHomeAssignment.service.impl;

import com.example.TakeHomeAssignment.dto.request.EmployeeRequest;
import com.example.TakeHomeAssignment.dto.response.CurrencyEmployeeResponse;
import com.example.TakeHomeAssignment.dto.response.EmployeeBonusResponse;
import com.example.TakeHomeAssignment.dto.response.GetEmployeeResponse;
import com.example.TakeHomeAssignment.model.Department;
import com.example.TakeHomeAssignment.model.Employee;
import com.example.TakeHomeAssignment.repository.DepartmentRepo;
import com.example.TakeHomeAssignment.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeBonusServiceImplTest {
    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private EmployeeRequest request;
    private Department department;
    private Employee employee;

    @BeforeEach
    void setup() {
        request = EmployeeRequest.builder()
                .empName("Hardik")
                .department("IT")
                .amount(5000)
                .currency("INR")
                .joiningDate("mar-31-2020")
                .exitDate("mar-01-2023")
                .build();

        department = Department.builder()
                .departmentName("IT")
                .build();

        employee = Employee.builder()
                .empName("Hardik")
                .department(department)
                .amount(5000)
                .currency("INR")
                .joiningDate(LocalDate.parse("mar-31-2020", EmployeeServiceImpl.DATE_FORMATTER))
                .exitDate(LocalDate.parse("mar-01-2023", EmployeeServiceImpl.DATE_FORMATTER))
                .build();
    }

    @Test
    void testSaveEmployees() {
        when(departmentRepo.findByDepartmentName("IT")).thenReturn(Optional.empty());
        when(departmentRepo.save(any(Department.class))).thenReturn(department);
        when(employeeRepo.saveAll(any())).thenReturn(Arrays.asList(employee));

        boolean result = employeeService.saveEmployeeList(Arrays.asList(request));

        verify(departmentRepo, times(1)).findByDepartmentName("IT");
        verify(departmentRepo, times(1)).save(any(Department.class));
        verify(employeeRepo, times(1)).saveAll(any());
        assertTrue(result);
    }

    @Test
    void testConvertRequestToEmployee() {
        when(departmentRepo.findByDepartmentName("IT")).thenReturn(Optional.of(department));

        Employee result = employeeService.convertRequestToEmployee(request);

        verify(departmentRepo, times(1)).findByDepartmentName("IT");
        assertEquals(employee.getEmpName(), result.getEmpName());
        assertEquals(employee.getCurrency(), result.getCurrency());
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getAmount(), result.getAmount());
    }

    @Test
    void testConvertRequestToEmployeeWithNewDepartment() {
        when(departmentRepo.findByDepartmentName("IT")).thenReturn(Optional.empty());
        when(departmentRepo.save(any(Department.class))).thenReturn(department);

        Employee result = employeeService.convertRequestToEmployee(request);

        verify(departmentRepo, times(1)).findByDepartmentName("IT");
        verify(departmentRepo, times(1)).save(any(Department.class));
        assertEquals(employee.getEmpName(), result.getEmpName());
        assertEquals(employee.getCurrency(), result.getCurrency());
        assertEquals(employee.getId(), result.getId());
        assertEquals(employee.getAmount(), result.getAmount());
    }

    @Test
    void testGetEmployeeBonusByDate() {

        Employee employee1 = Employee.builder()
                .empName("Hardik")
                .amount(5000)
                .currency("USD")
                .build();

        Employee employee2 = Employee.builder()
                .empName("Reetika")
                .amount(10000)
                .currency("INR")
                .build();

        when(employeeRepo.findEligibleEmployees(any(LocalDate.class)))
                .thenReturn(Arrays.asList(employee1, employee2));

        GetEmployeeResponse response = employeeService.getEmployeeBonusByDate("\"may-05-2021\"");

        assertEquals("", response.getErrorMessage());

        List<CurrencyEmployeeResponse> data = response.getData();
        assertEquals(2, data.size());

        CurrencyEmployeeResponse inrResponse = data.stream()
                .filter(r -> r.getCurrency().equals("INR"))
                .findFirst()
                .orElse(null);
        assertNotNull(inrResponse);
        assertEquals(1, inrResponse.getEmployees().size());
        assertEquals("Reetika", inrResponse.getEmployees().get(0).getEmpName());

        CurrencyEmployeeResponse usdResponse = data.stream()
                .filter(r -> r.getCurrency().equals("USD"))
                .findFirst()
                .orElse(null);
        assertNotNull(usdResponse);
        assertEquals(1, usdResponse.getEmployees().size());
        assertEquals("Hardik", usdResponse.getEmployees().get(0).getEmpName());
    }
}