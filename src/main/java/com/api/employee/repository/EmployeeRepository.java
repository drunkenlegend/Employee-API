package com.api.employee.repository;

import com.api.employee.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, Integer> {
  List<EmployeeEntity> findByEmpEmail(String email);

  Page<EmployeeEntity> findAll(Specification<EmployeeEntity> spec, Pageable pageable);

  List<EmployeeEntity> findAll();
}
