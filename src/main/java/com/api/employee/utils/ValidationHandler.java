//package com.api.employee.utils;
//
//import com.api.employee.exception.EmployeeConflictException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import java.net.http.HttpHeaders;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//public class ValidationHandler extends ResponseEntityExceptionHandler {
//
//  //  private static final Logger LOGGER = LoggerFactory.getLogger(CustomControllerAdvice.class);
//
//  @Override
//  protected ResponseEntity<Object> handleMethodArgumentNotValid(
//      MethodArgumentNotValidException ex,
//      HttpHeaders headers,
//      HttpStatus status,
//      WebRequest request) {
//    Map<String, String> errors = new HashMap<>();
//    ex.getBindingResult()
//        .getAllErrors()
//        .forEach(
//            (error) -> {
//              String fieldname = ((FieldError) error).getField();
//              String message = error.getDefaultMessage();
//              errors.put(fieldname, message);
//            });
//    return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
//  }
//    @ExceptionHandler(EmployeeConflictException.class)
//    public ResponseEntity<Message> customHandleNotFound(EmployeeConflictException ex, WebRequest request) {
//
//        Message errors = new Message();
//        errors.setTimestamp(LocalDateTime.now());
//        errors.setDesc(ex.getMessage());
//        errors.setStatusCode(ex.getErrorCode());
//
//        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
//
//    }
//}
