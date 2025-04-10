package com.vst.demo.repository;

import com.vst.demo.enitity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {


    @Query(
            value = "select * from departments where name = :name",
            nativeQuery = true)
    Optional<Department> findByName(String name);
}
