package com.api.employee.service;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.utils.Message;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
  Page<EmployeeDTO> getAllEmployees(
      Integer pageNo, Integer pageSize, String sortBy, String keyword);

  EmployeeDTO createEmployee(EmployeeDTO employeeEntity, Integer deptId);

  EmployeeDTO getEmployeeById(Integer id);

  EmployeeDTO updateEmployeeById(Integer id, EmployeeDTO employeeEntity);

  EmployeeDTO patchEmployeeById(Integer id, EmployeeDTO employeeEntity);

  List<EmployeeDTO> batchCreateEmployee(List<EmployeeDTO> employeeDTOS);

  Message deleteEmployeeById(Integer id);
}
