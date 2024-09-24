package com.example.TakeHomeAssignment.dto.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequest {
    @NotBlank(message = "Employee name should not be blank")
    private String empName;

    //Using a regex for 3 department names because only they are provided in the assignment.
    @NotBlank(message = "Department should not be blank")
    @Pattern(regexp = "(IT|accounts|Operations)"
            ,message = "Only valid department names are:IT,accounts,Operations")
    private String department;

    @NotNull(message = "Amount should not be blank")
    @Positive(message = "Amount must be positive")
    private Integer amount;

    // Using a regex for INR and USD as provided in the assignment.
    @NotBlank(message = "Currency can`t be blank")
    @Pattern(regexp = "^[A-Z]{3}$",
            message = "Currency code should be either INR or USD")
    private String currency;


    @NotBlank(message = "Joining date can`t be blank")
    @Pattern(regexp = "^(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)-([0-2][0-9]|3[01])-(19|20)\\d{2}$",
            message = "Joining date format is not valid")
    private String joiningDate;

    @Pattern(regexp = "^(jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)-([0-2][0-9]|3[01])-(19|20)\\d{2}$",
            message = "Exit date format is not valid")
    private String exitDate;
}
