package com.example.TakeHomeAssignment.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeListRequest {
    @NotNull(message = "Employees list cannot be null")
    @Size(min = 1, message = "Employees list must contain at least one employee")
    @Valid
    private List<EmployeeRequest> employees;
}
