package com.api.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class EmployeeEntity implements Serializable {

    @EmbeddedId
    private EmployeeKey employeeKey;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String empFirstName;

    @Size(max = 100)
    private String empLastName;

    @NotEmpty
    @Email
    private String empEmail;

    @NotNull
    @Pattern(
            regexp = "(^([+]\\d{2}([ ])?)?\\d{10}$)",
            message = "Number should be in format: {+91 1234567890, +911234567890, 1234567890}")
    private String empContactNumber;

    @ManyToOne
    private AddressEntity addressEntity;


}
