package com.example.TakeHomeAssignment.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeBonusResponse {
    private String empName;
    private Integer amount;
}
