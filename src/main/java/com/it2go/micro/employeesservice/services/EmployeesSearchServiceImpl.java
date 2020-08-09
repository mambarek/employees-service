package com.it2go.micro.employeesservice.services;

import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity_;
import com.it2go.micro.employeesservice.search.PredicateBuilder;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItem;
import com.it2go.micro.employeesservice.search.table.EmployeesSearchTemplate;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeesSearchServiceImpl implements EmployeesSearchService {

  final EntityManager entityManager;

  @Override
  public EmployeeTableItemList filterEmployees(EmployeesSearchTemplate employeesSearchTemplate) {
    System.out.println("Call of filterEmployees with template " + employeesSearchTemplate);
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<EmployeeTableItem> criteriaQuery = cb.createQuery(EmployeeTableItem.class);
    Root<EmployeeEntity> employeeRoot = criteriaQuery.from(EmployeeEntity.class);

    final CompoundSelection<EmployeeTableItem> compoundSelection = cb
        .construct(EmployeeTableItem.class,
            employeeRoot.get(EmployeeEntity_.publicId),
            employeeRoot.get(EmployeeEntity_.firstName),
            employeeRoot.get(EmployeeEntity_.lastName),
            employeeRoot.get(EmployeeEntity_.birthDate),
            employeeRoot.get(EmployeeEntity_.salary),
            employeeRoot.get(EmployeeEntity_.traveling),
            employeeRoot.get(EmployeeEntity_.weekendWork),
            employeeRoot.get(EmployeeEntity_.createdAt));

    final CriteriaQuery<EmployeeTableItem> select = criteriaQuery.select(compoundSelection);

    PredicateBuilder<EmployeeEntity> predicateBuilder = null;
    if(employeesSearchTemplate.getFilters() != null) {
      predicateBuilder = PredicateBuilder
          .createPredicates(cb, employeeRoot, employeesSearchTemplate.getFilters());

      select.where(predicateBuilder.getPredicates().toArray(new Predicate[0]));
    }

    // Order by
    Order orderBy = null;

    if (employeesSearchTemplate.getOrderBy() != null && !employeesSearchTemplate.getOrderBy()
        .isEmpty()) {
      switch (employeesSearchTemplate.getOrderDirection()) {
        case "asc":
          orderBy = cb.asc(employeeRoot.get(employeesSearchTemplate.getOrderBy()));
          break;
        case "desc":
          orderBy = cb.desc(employeeRoot.get(employeesSearchTemplate.getOrderBy()));
          break;
      }
    }

    if (orderBy != null) {
      select.orderBy(orderBy);
    }

    final TypedQuery<EmployeeTableItem> query = entityManager.createQuery(select);

    // set query parameter if exists
    if(predicateBuilder != null) {
      predicateBuilder.getParamMap().forEach(query::setParameter);
    }

    if (employeesSearchTemplate.getMaxResult() > 0) {
      query.setMaxResults(employeesSearchTemplate.getMaxResult());
    }

    query.setFirstResult(employeesSearchTemplate.getOffset());

    final List<EmployeeTableItem> resultList = query.getResultList();
    System.out.println("resultList = " + resultList.size());

    return new EmployeeTableItemList(resultList);
  }
}
