package com.it2go.micro.employeesservice.search.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * While parsing list is often error prone in json so a wrapper object works fine
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeTableItemList implements Serializable {
  private List<EmployeeTableItem> resultList = new ArrayList<>();
}
