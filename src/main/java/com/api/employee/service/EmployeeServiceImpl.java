package com.api.employee.service;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.entity.EmployeeEntity;
import com.api.employee.exception.EmployeeConflictException;
import com.api.employee.mapper.EmployeeMapper;
import com.api.employee.repository.EmployeeRepository;
import com.api.employee.specification.EmployeeSpecification;
import com.api.employee.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("v1")
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired private EmployeeRepository repository;

  @Autowired private EmployeeMapper mapper;

  public Page<EmployeeDTO> getAllEmployees(
      Integer pageNo, Integer pageSize, String sortBy, String keyword) {
    Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

    Page<EmployeeEntity> result =
        repository.findAll(
            Specification.where(EmployeeSpecification.likeOperation("empFirstName", keyword))
                .or(EmployeeSpecification.likeOperation("empLastName", keyword))
                .or(EmployeeSpecification.likeOperation("empEmail", keyword)),
            paging);

    Page<EmployeeDTO> pageResult =
        result.map(employeeEntity -> mapper.convertToEmployeeDTO(employeeEntity));

    // No exception here, if no employee found then return a page with 0 employees
    return pageResult;
  }

  public EmployeeDTO createEmployee(EmployeeDTO employee, Integer deptId) {
    List<EmployeeEntity> employeeEntityList = repository.findByEmpEmail(employee.getEmpEmail());
    if (employeeEntityList.size() > 0) {
      throw new EmployeeConflictException("Conflicting Details Passed", "409");
    }

    EmployeeEntity employeeEntity = (mapper.convertToEmployeeEntity(employee));
    employeeEntity.setDeptId(deptId);

    return mapper.convertToEmployeeDTO(repository.save(employeeEntity));
  }

  public EmployeeDTO getEmployeeById(Integer id) {
    Optional<EmployeeEntity> employee = repository.findById(id);
    if (employee.isPresent()) return mapper.convertToEmployeeDTO(employee.get());
    else throw new EmployeeConflictException("No Employee with this Id Found", "404");
  }

  public EmployeeDTO updateEmployeeById(Integer id, EmployeeDTO employee) {
    Optional<EmployeeEntity> emp = repository.findById(id);
    if (emp.isPresent()) {
      repository.save(mapper.convertToEmployeeEntity(employee));
      return employee;
    } else {
      throw new EmployeeConflictException("No Employee found to update", "404");
    }
  }

  public EmployeeDTO patchEmployeeById(Integer id, EmployeeDTO employeeDTO) {
    Optional<EmployeeEntity> emp = repository.findById(id);
    if (emp.isPresent()) {
      EmployeeEntity employeeEntity = emp.get();
      if (employeeDTO.getEmpEmail() != null) employeeEntity.setEmpEmail(employeeDTO.getEmpEmail());
      if (employeeDTO.getEmpContactNumber() != null)
        employeeEntity.setEmpContactNumber(employeeDTO.getEmpContactNumber());
      if (employeeDTO.getEmpFirstName() != null)
        employeeEntity.setEmpFirstName(employeeDTO.getEmpFirstName());
      if (employeeDTO.getEmpLastName() != null)
        employeeEntity.setEmpLastName(employeeDTO.getEmpLastName());
      repository.save(employeeEntity);
      return mapper.convertToEmployeeDTO(employeeEntity);
    } else {
      throw new EmployeeConflictException("No Employee found to update", "404");
    }
  }

  public Message deleteEmployeeById(Integer id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
    }
    return new Message("Successful", "Employee Deleted", "200");
  }

  public List<EmployeeDTO> batchCreateEmployee(@Valid List<EmployeeDTO> employeeDTOS) {
    List<EmployeeDTO> empDTOs = new ArrayList<>();
    repository
        .saveAll(
            employeeDTOS.stream()
                .map(employeeDTO -> mapper.convertToEmployeeEntity(employeeDTO))
                .collect(Collectors.toList()))
        .forEach(employeeEntity -> empDTOs.add(mapper.convertToEmployeeDTO(employeeEntity)));
    return empDTOs;
  }
}
