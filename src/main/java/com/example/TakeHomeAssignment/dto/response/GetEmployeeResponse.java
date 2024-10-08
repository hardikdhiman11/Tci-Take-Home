package com.example.TakeHomeAssignment.dto.response;

import lombok.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetEmployeeResponse {
    private String errorMessage;
    private List data;
}
