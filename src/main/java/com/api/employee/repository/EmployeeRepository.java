package com.api.employee.repository;

import com.api.employee.entity.EmployeeEntity;
import com.api.employee.entity.EmployeeKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, EmployeeKey> {
  List<EmployeeEntity> findByEmpEmail(String email);

  List<EmployeeEntity> findByEmpContactNumber(String number);

  Optional<EmployeeEntity> findByEmployeeKey(EmployeeKey employeeKey);

  Boolean existsByEmployeeKey(EmployeeKey employeeKey);

  void deleteByEmployeeKey(EmployeeKey employeeKey);

  Page<EmployeeEntity> findAll(Specification<EmployeeEntity> spec, Pageable pageable);

  List<EmployeeEntity> findAll();
}
