package com.api.employee.mapper;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.entity.AddressEntity;
import com.api.employee.entity.EmployeeEntity;
import com.api.employee.entity.EmployeeKey;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapper {


    public EmployeeDTO convertToEmployeeDTO(EmployeeEntity employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpEmail(employee.getEmpEmail());
        employeeDTO.setEmpContactNumber(employee.getEmpContactNumber());
        employeeDTO.setEmpFirstName(employee.getEmpFirstName());
        employeeDTO.setEmpLastName(employee.getEmpLastName());
        AddressEntity addressEntity;
        addressEntity = employee.getAddressEntity();
        EmployeeKey employeeKey=employee.getEmployeeKey();
        employeeDTO.setEmpId(employeeKey.getEmpId());
        employeeDTO.setCity(addressEntity.getCity());
        employeeDTO.setCountry(addressEntity.getCountry());
        employeeDTO.setState(addressEntity.getState());
        return employeeDTO;
    }

    public EmployeeEntity convertToEmployeeEntity(EmployeeDTO employeeDto) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setEmpEmail(employeeDto.getEmpEmail());
        EmployeeKey employeeKey=new EmployeeKey();
        employeeKey.setEmpId(employeeDto.getEmpId());
        employeeEntity.setEmployeeKey(employeeKey);
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
