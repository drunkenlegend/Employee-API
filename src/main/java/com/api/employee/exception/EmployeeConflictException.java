package com.api.employee.exception;

public class EmployeeConflictException extends EmployeeException {
  public EmployeeConflictException(String message, String errorCode) {
    super(message, errorCode);
  }

  public EmployeeConflictException(Throwable original, String errorCode) {
    super(original, errorCode);
  }
}
