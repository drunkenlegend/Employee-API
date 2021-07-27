package com.api.employee.mapper;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.entity.EmployeeEntity;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {

  private final ModelMapper modelMapper = new ModelMapper();

  public EmployeeDTO convertToEmployeeDTO(EmployeeEntity employee) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    return modelMapper.map(employee, EmployeeDTO.class);
  }

  public EmployeeEntity convertToEmployeeEntity(EmployeeDTO employee) {
    modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    return modelMapper.map(employee, EmployeeEntity.class);
  }
}
