package com.it2go.micro.employeesservice.services.impl;

import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity;
import com.it2go.micro.employeesservice.persistence.jpa.entities.EmployeeEntity_;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItem;
import com.it2go.micro.employeesservice.search.table.EmployeeTableItemList;
import com.it2go.micro.employeesservice.services.EmployeesSearchService;
import com.it2go.util.jpa.search.PredicateBuilder;
import com.it2go.util.jpa.search.SearchOrder;
import com.it2go.util.jpa.search.SearchTemplate;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeesSearchServiceImpl implements EmployeesSearchService {

  final EntityManager entityManager;

  @Override
  public EmployeeTableItemList filterEmployees(SearchTemplate searchTemplate) {
    log.debug("Call of filterEmployees with template " + searchTemplate);
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<EmployeeTableItem> criteriaQuery = cb.createQuery(EmployeeTableItem.class);
    Root<EmployeeEntity> employeeRoot = criteriaQuery.from(EmployeeEntity.class);

    // Achtung hier ist die Reihenfolge wichtig damit die selektierten spalten zu den Attributen
    // von EmployeeTableItem passen
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

/*            employeeRoot.get(EmployeeEntity_.ADDRESS).get(AddressEntity_.STREET_ONE),
            employeeRoot.get(EmployeeEntity_.ADDRESS).get(AddressEntity_.BUILDING_NR),
            employeeRoot.get(EmployeeEntity_.ADDRESS).get(AddressEntity_.CITY),
            employeeRoot.get(EmployeeEntity_.ADDRESS).get(AddressEntity_.ZIP_CODE),
            employeeRoot.get(EmployeeEntity_.ADDRESS).get(AddressEntity_.COUNTRY_CODE));*/

    final CriteriaQuery<EmployeeTableItem> select = criteriaQuery.select(compoundSelection);

    PredicateBuilder predicateBuilder = null;
    if(searchTemplate.getFilters() != null) {
      predicateBuilder = PredicateBuilder
          .createPredicates(cb, employeeRoot, searchTemplate.getFilters());

      select.where(predicateBuilder.getPredicates().toArray(new Predicate[0]));
    }

    if (searchTemplate.getOrderBy() != null && !searchTemplate.getOrderBy().isEmpty()) {
      SearchOrder orderDirection = searchTemplate.getOrderDirection();
      if(orderDirection == null) orderDirection = SearchOrder.ASC;
      Order orderBy = null;
      switch (orderDirection) {
        case ASC:
          orderBy = cb.asc(employeeRoot.get(searchTemplate.getOrderBy()));
          break;
        case DESC:
          orderBy = cb.desc(employeeRoot.get(searchTemplate.getOrderBy()));
          break;
      }
      select.orderBy(orderBy);
    }

    final TypedQuery<EmployeeTableItem> query = entityManager.createQuery(select);

    // set query parameter if exists
    if(predicateBuilder != null) {
      predicateBuilder.getParamMap().forEach(query::setParameter);
    }

    if (searchTemplate.getMaxResult() > 0) {
      query.setMaxResults(searchTemplate.getMaxResult());
    }

    query.setFirstResult(searchTemplate.getOffset());

    final List<EmployeeTableItem> resultList = query.getResultList();

    return new EmployeeTableItemList(resultList);
  }
}
