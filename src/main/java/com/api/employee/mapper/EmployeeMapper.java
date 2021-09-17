package com.api.employee.mapper;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.entity.AddressEntity;
import com.api.employee.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {


    public EmployeeDTO convertToEmployeeDTO(EmployeeEntity employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpEmail(employee.getEmpEmail());
        employeeDTO.setEmpContactNumber(employee.getEmpContactNumber());
        employeeDTO.setEmpId(employee.getEmpId());
        employeeDTO.setEmpFirstName(employee.getEmpFirstName());
        employeeDTO.setEmpLastName(employee.getEmpLastName());
        AddressEntity addressEntity;
        addressEntity = employee.getAddressEntity();
        employeeDTO.setCity(addressEntity.getCity());
        employeeDTO.setCountry(addressEntity.getCountry());
        employeeDTO.setState(addressEntity.getState());
        return employeeDTO;
    }

    public EmployeeEntity convertToEmployeeEntity(EmployeeDTO employeeDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmpEmail(employeeDto.getEmpEmail());
        employeeEntity.setEmpId(employeeDto.getEmpId());
        employeeEntity.setEmpContactNumber(employeeDto.getEmpContactNumber());
        employeeEntity.setEmpFirstName(employeeDto.getEmpFirstName());
        employeeEntity.setEmpLastName(employeeDto.getEmpLastName());
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setCity(employeeDto.getCity());
        addressEntity.setCountry(employeeDto.getCountry());
        addressEntity.setState(employeeDto.getState());
        employeeEntity.setAddressEntity(addressEntity);
        return employeeEntity;
    }
}
