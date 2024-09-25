package com.example.TakeHomeAssignment.dto.response;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CurrencyEmployeeResponse {
    private String currency;
    private List<EmployeeBonusResponse> employees;
}
