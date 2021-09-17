package com.api.employee.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.*;
import java.io.Serializable;

@Getter
@Setter
public class EmployeeDTO implements Serializable {
  @Id private Integer empId;

  @NotEmpty
  @Size(min = 2, max = 100)
  private String empFirstName;

  @Size(max = 100)
  private String empLastName;

  @NotEmpty @Email private String empEmail;

  @NotNull
  @Pattern(
      regexp = "(^([+]\\d{2}([ ])?)?\\d{10}$)",
      message = "Number should be in format: {+91 1234567890, +911234567890, 1234567890}")
  private String empContactNumber;

  @NotEmpty
  private String city;
  @NotEmpty
  private String state;
  @NotEmpty
  private String country;
}
