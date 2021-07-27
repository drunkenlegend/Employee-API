package com.api.employee.service;

import com.api.employee.entity.EmployeeEntity;
import com.api.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PushService {
  @Autowired private EmployeeRepository pushRepository;

  public Iterable<EmployeeEntity> list() {
    return pushRepository.findAll();
  }

  public Iterable<EmployeeEntity> save(List<EmployeeEntity> employees) {
    return pushRepository.saveAll(employees);
  }
}
