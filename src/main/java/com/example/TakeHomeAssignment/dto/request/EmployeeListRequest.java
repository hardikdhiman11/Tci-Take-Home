package com.example.TakeHomeAssignment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeListRequest {
    @Valid
    @NotNull(message = "Employees list cannot be null")
    @Size(min = 1, message = "Employees list must contain at least one employee")
    private List<EmployeeRequest> employees;
}
