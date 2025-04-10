package com.vst.demo.service;

import com.vst.demo.enitity.Department;
import com.vst.demo.exceptions.DepartmentNotFoundException;
import com.vst.demo.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public Department save(Department department) {
        var existing = departmentRepository.findByName(department.getName());
        if (existing.isPresent())
            return departmentRepository.save(department);
        else throw new DepartmentNotFoundException("Данный отдел уже существует");
    }

    public void deleteById(int id) {
        var removable = departmentRepository.findById(id);
        if (removable.isPresent()) {
            departmentRepository.deleteById(id);

        }
        else throw new DepartmentNotFoundException("Удаление невозможно");
    }


}
