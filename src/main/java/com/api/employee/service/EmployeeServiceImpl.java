package com.api.employee.service;

import com.api.employee.dto.EmployeeDTO;
import com.api.employee.entity.AddressEntity;
import com.api.employee.entity.EmployeeEntity;
import com.api.employee.entity.EmployeeKey;
import com.api.employee.exception.EmployeeConflictException;
import com.api.employee.mapper.EmployeeMapper;
import com.api.employee.repository.AddressRepository;
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

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Qualifier("v1")
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmployeeMapper mapper;

    public Page<EmployeeDTO> getAllEmployees(
            Integer pageNo, Integer pageSize, String sortBy, String keyword) {
        if (sortBy.equals("empId")) {
            sortBy = "employeeKey.empId";
        }
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC, sortBy));

        Page<EmployeeEntity> result =
                repository.findAll(
                        Specification.where(EmployeeSpecification.likeOperation("empFirstName", keyword))
                                .or(EmployeeSpecification.likeOperation("empLastName", keyword))
                                .or(EmployeeSpecification.likeOperation("empEmail", keyword))
                                .or(EmployeeSpecification.likeOperation("employeeKey.deptId", keyword))
                                .or(EmployeeSpecification.likeOperation("employeeKey.empId", keyword))
                                .or(EmployeeSpecification.likeOperation("addressEntity.pinCode", keyword)),
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
        }else{
            employeeEntityList=repository.findByEmpContactNumber(employee.getEmpContactNumber());
            if (employeeEntityList.size() > 0) {
                throw new EmployeeConflictException("Conflicting Details Passed", "409");
            }else{
                EmployeeKey employeeKey=new EmployeeKey(employee.getEmpId(),deptId);
                Optional<EmployeeEntity> employeeEntity=repository.findByEmployeeKey(employeeKey);
                if (employeeEntity.isPresent()) {
                    throw new EmployeeConflictException("Conflicting Details Passed", "409");
                }
            }
        }
        AddressEntity addressEntity=new AddressEntity();
        addressEntity.setPinCode(employee.getPinCode());
        addressEntity.setCity(employee.getCity());
        addressEntity.setState(employee.getState());
        addressEntity.setCountry(employee.getCountry());
        addressRepository.save(addressEntity);
        EmployeeEntity employeeEntity = (mapper.convertToEmployeeEntity(employee));
        employeeEntity.setAddressEntity(addressEntity);
        employeeEntity.getEmployeeKey().setDeptId(deptId);

        return mapper.convertToEmployeeDTO(repository.save(employeeEntity));
    }

    public EmployeeDTO getEmployeeById(Integer id,Integer dept) {
        EmployeeKey employeeKey=new EmployeeKey();
        employeeKey.setEmpId(id);
        employeeKey.setDeptId(dept);
        Optional<EmployeeEntity> employee = repository.findByEmployeeKey(employeeKey);
        if (employee.isPresent()) return mapper.convertToEmployeeDTO(employee.get());
        else throw new EmployeeConflictException("No Employee with this Id Found", "404");
    }

    public EmployeeDTO updateEmployeeById(Integer id,Integer dept, EmployeeDTO employee) {
        EmployeeKey employeeKey=new EmployeeKey();
        employeeKey.setEmpId(id);
        employeeKey.setDeptId(dept);
        Optional<EmployeeEntity> emp = repository.findByEmployeeKey(employeeKey);
        if (emp.isPresent()) {
            repository.save(mapper.convertToEmployeeEntity(employee));
            return employee;
        } else {
            throw new EmployeeConflictException("No Employee found to update", "404");
        }
    }

    public EmployeeDTO patchEmployeeById(Integer id,Integer dept, EmployeeDTO employeeDTO) {
        EmployeeKey employeeKey=new EmployeeKey();
        employeeKey.setEmpId(id);
        employeeKey.setDeptId(dept);
        Optional<EmployeeEntity> emp = repository.findByEmployeeKey(employeeKey);
        if (emp.isPresent()) {
            EmployeeEntity employeeEntity = emp.get();
            if (employeeDTO.getEmpEmail() != null) employeeEntity.setEmpEmail(employeeDTO.getEmpEmail());
            if (employeeDTO.getEmpContactNumber() != null)
                employeeEntity.setEmpContactNumber(employeeDTO.getEmpContactNumber());
            if (employeeDTO.getEmpFirstName() != null)
                employeeEntity.setEmpFirstName(employeeDTO.getEmpFirstName());
            if (employeeDTO.getEmpLastName() != null)
                employeeEntity.setEmpLastName(employeeDTO.getEmpLastName());
            if(employeeDTO.getCity()!=null)
                employeeEntity.getAddressEntity().setCity(employeeDTO.getCity());
            if(employeeDTO.getState()!=null)
                employeeEntity.getAddressEntity().setState(employeeDTO.getState());
            if(employeeDTO.getCountry()!=null)
                employeeEntity.getAddressEntity().setCountry(employeeDTO.getCountry());
            repository.save(employeeEntity);
            return mapper.convertToEmployeeDTO(employeeEntity);
        } else {
            throw new EmployeeConflictException("No Employee found to update", "404");
        }
    }

    public Message deleteEmployeeById(Integer id,Integer dept) {
        EmployeeKey employeeKey=new EmployeeKey();
        employeeKey.setEmpId(id);
        employeeKey.setDeptId(dept);
        if (repository.existsByEmployeeKey(employeeKey)) {
            repository.deleteByEmployeeKey(employeeKey);
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
