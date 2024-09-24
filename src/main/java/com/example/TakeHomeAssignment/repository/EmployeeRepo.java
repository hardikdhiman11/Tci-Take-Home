package com.example.TakeHomeAssignment.repository;

import com.example.TakeHomeAssignment.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Long> {
    @Query("SELECT e FROM Employee e WHERE e.joiningDate <= :date AND (e.exitDate IS NULL OR e.exitDate >= :date)")
    List<Employee> findEligibleEmployees(@Param("date") LocalDate date);
}
