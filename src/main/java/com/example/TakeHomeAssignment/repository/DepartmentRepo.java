package com.example.TakeHomeAssignment.repository;

import com.example.TakeHomeAssignment.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepo extends JpaRepository<Department,Long> {
    @Query("SELECT d from Department d WHERE departmentName = :departmentName")
    Optional<Department> findByDepartmentName(@Param("departmentName") String departmentName);
}
