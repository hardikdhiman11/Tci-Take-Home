package com.example.TakeHomeAssignment.service.impl;

import com.example.TakeHomeAssignment.dto.request.EmployeeRequest;
import com.example.TakeHomeAssignment.model.Department;
import com.example.TakeHomeAssignment.repository.DepartmentRepo;
import com.example.TakeHomeAssignment.repository.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class EmployeeBonusServiceImplTest {

    @InjectMocks
    private EmployeeBonusServiceImpl employeeBonusService;

    @Mock
    private EmployeeRepo employeeRepo;

    @Mock
    private DepartmentRepo departmentRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveEmployeeList_Success() {
        // Given
        List<EmployeeRequest> dtoRequests = new ArrayList<>();
        dtoRequests.add(new EmployeeRequest("John Doe", "HR", 5000, "USD", "2024-01-01", "2024-12-31"));

        Department department = Department.builder().departmentName("HR").build();
        when(departmentRepo.findByDepartmentName("HR")).thenReturn(Optional.of(department));
        when(employeeRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

        // When
        boolean result = employeeBonusService.saveEmployeeList(dtoRequests);

        // Then
        verify(departmentRepo, times(1)).findByDepartmentName("HR");
        verify(employeeRepo, times(1)).saveAll(anyList());
        assertTrue(result);
    }

    @Test
    void testSaveEmployeeList_NewDepartment() {
        // Given
        List<EmployeeRequest> dtoRequests = new ArrayList<>();
        dtoRequests.add(new EmployeeRequest("Jane Doe", "Finance", 6000, "USD", "2024-01-01", "2024-12-31"));

        when(departmentRepo.findByDepartmentName("Finance")).thenReturn(Optional.empty());
        when(employeeRepo.saveAll(anyList())).thenReturn(new ArrayList<>());

        // When
        boolean result = employeeBonusService.saveEmployeeList(dtoRequests);

        // Then
        verify(departmentRepo, times(1)).findByDepartmentName("Finance");
        verify(departmentRepo, times(1)).save(any(Department.class)); // Verify that a new department is saved
        verify(employeeRepo, times(1)).saveAll(anyList());
        assertTrue(result);
    }

    @Test
    void testSaveEmployeeList_Exception() {
        // Given
        List<EmployeeRequest> dtoRequests = new ArrayList<>();
        dtoRequests.add(new EmployeeRequest("Alice Smith", "IT", 7000, "USD", "2024-01-01", "2024-12-31"));

        when(departmentRepo.findByDepartmentName("IT")).thenThrow(new RuntimeException("Database error"));

        // When
        boolean result = employeeBonusService.saveEmployeeList(dtoRequests);

        // Then
        verify(departmentRepo, times(1)).findByDepartmentName("IT");
        verify(employeeRepo, never()).saveAll(anyList()); // Ensure saveAll is never called
        assertFalse(result);
    }
}