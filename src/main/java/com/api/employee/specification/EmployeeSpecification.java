package com.api.employee.specification;

import com.api.employee.entity.EmployeeEntity;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecification {

  public static Specification<EmployeeEntity> likeOperation(
      final String column, final String keyword) {
    if (keyword == null || column == null) {
      return null;
    }
    // SELECT ... FROM Customer c WHERE c.name LIKE %name%;
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(root.get(column), String.format("%%%s%%", keyword));
  }

  public static Specification<EmployeeEntity> equalsOperation(
      final String column, final String keyword) {
    if (keyword == null || column == null) {
      return null;
    }
    // SELECT ... FROM Customer c WHERE c.name equal %name%;
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.equal(root.get(column), String.format("%s", keyword));
  }
}
